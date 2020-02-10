package com.onewisebit.scpescape.model.repositories

import android.content.Context

open class JSONRepository(val context: Context) {

    private val systemAssetFolders: Array<String> = arrayOf("images","webkit")

    fun getFiles(path: String): Array<String>? {
        return context.assets.list(path)
    }

    //TODO: check if this returns true also for empty directories
    fun isFile(path: String): Boolean {
        return context.assets.list(path).isNullOrEmpty()
    }

    fun searchFile(fileName: String, startingPath: String = "", maxDepth: Int = 1, skipSystemFolders: Boolean = true): MutableList<String> {
        val results: MutableList<String> = mutableListOf()
        val pathList = context.assets.list(startingPath)
        val regex = Regex("($fileName)$")

        if (pathList != null) {
            for (path in pathList) {
                // skip if system folder
                if (skipSystemFolders && systemAssetFolders.contains(path))
                    continue

                val newPath = if (startingPath == "" ) path else startingPath.plus("/").plus(path)
                // the path is a file
                if (isFile(newPath)){
                    // if the name is correct we add it to the list
                    if (regex.containsMatchIn(newPath))
                        results.add(newPath)
                }
                // the path is a directory
                else{
                    // we go deeper in the folder structure if required
                    // 1 is equivalent to looking only in the current directory
                    // system folders are only in root directory so we set skipSystemFolders to false
                    if (maxDepth > 1)
                        results.addAll(searchFile(fileName,newPath,maxDepth-1,skipSystemFolders = false))
                }


            }
        }

        return results
    }

}