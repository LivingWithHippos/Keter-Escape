package com.onewisebit.scpescape.model.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.utilities.MODE_FILE
import com.onewisebit.scpescape.utilities.smartList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//todo: many functions here could be moved to an extension, see [Extensions.kt]
open class JSONRepository(val context: Context) {

    private val systemAssetFolders: Array<String> = arrayOf("images", "webkit")

    fun getFiles(path: String): Array<String>? {
        // on some phones the path works with / at the end, on others not.
        //val result = context.assets.list(path)
        return context.assets.smartList(path)
    }

    //TODO: check if this returns true also for empty directories
    fun isFile(path: String): Boolean {
        return context.assets.list(path).isNullOrEmpty()
    }

    fun searchFile(
        fileName: String,
        startingPath: String = "",
        maxDepth: Int = 1,
        skipSystemFolders: Boolean = true
    ): MutableList<String> {
        val results: MutableList<String> = mutableListOf()
        val pathList = context.assets.list(startingPath)
        val regex = Regex("($fileName)$")

        if (pathList != null) {
            for (path in pathList) {
                // skip if system folder
                if (skipSystemFolders && systemAssetFolders.contains(path))
                    continue

                val newPath = if (startingPath == "") path else startingPath.plus("/").plus(path)
                // the path is a file
                if (isFile(newPath)) {
                    // if the name is correct we add it to the list
                    if (regex.containsMatchIn(newPath))
                        results.add(newPath)
                }
                // the path is a directory
                else {
                    // we go deeper in the folder structure if required
                    // 1 is equivalent to looking only in the current directory
                    // system folders are only in root directory so we set skipSystemFolders to false
                    if (maxDepth > 1)
                        results.addAll(
                            searchFile(
                                fileName,
                                newPath,
                                maxDepth - 1,
                                skipSystemFolders = false
                            )
                        )
                }


            }
        }

        return results
    }

    suspend fun getModeFolder(modeId: Int): String? =
        withContext(Dispatchers.IO) {
            var found: Boolean = false
            var modePath: String? = null
            // check in folders two levels in (first level is the assets folder)
            val modePaths: MutableList<String> = searchFile(MODE_FILE, maxDepth = 2)

            pathLoop@ for (path in modePaths) {
                if (found)
                    break@pathLoop

                try {
                    context.assets.open(path).use { inputStream ->
                        JsonReader(inputStream.reader()).use { jsonReader ->
                            // load the current mode.json
                            val modeType = object : TypeToken<List<ModeDataClass>>() {}.type
                            val modesList: List<ModeDataClass> =
                                Gson().fromJson(jsonReader, modeType)

                            // check if it's the mode we're looking for
                            modeLoop@ for (parsedMode in modesList) {
                                if (parsedMode.id == modeId) {
                                    // we get the mode path from the mode.json path
                                    modePath = path.removeSuffix(MODE_FILE)
                                    found = true
                                    break@modeLoop
                                }
                            }
                        }
                    }
                } catch (ex: Exception) {
                    Log.e(TAG, "Error reading file in path $path", ex)
                }
            }
            modePath
        }


    companion object {
        private val TAG = JSONRepository::class.java.simpleName
    }

}