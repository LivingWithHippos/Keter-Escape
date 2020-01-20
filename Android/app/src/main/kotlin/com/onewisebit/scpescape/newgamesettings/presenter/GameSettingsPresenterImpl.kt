package com.onewisebit.scpescape.newgamesettings.presenter

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.newgamesettings.GameSettingsContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GameSettingsPresenterImpl(
    gView: GameSettingsContract.GameSettingsView,
    gModel: GameSettingsContract.GameSettingsModel
) : GameSettingsContract.GameSettingsPresenter {

    private var view: GameSettingsContract.GameSettingsView = gView
    private var model: GameSettingsContract.GameSettingsModel = gModel

    private val gameID: MutableLiveData<Long> = MutableLiveData()

    //TODO: find a better name for these methods
    override fun onNewGame(): LiveData<Long> {
        return gameID
    }

    @SuppressLint("CheckResult")
    override fun createNewGame(gameMode: Int, gameType: Int) {
        model.createGame(gameMode, gameType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { game -> gameID.postValue(game) },
                { error ->
                    Log.d(TAG,"Error getting game id from GameSettingsModel into presenter: ",error)
                }
            )
    }

    override fun saveSetting(key: String, value: String) {
        TODO("Save the settings chosen")
    }

    override fun gameSettingsAccepted(game: Game) {
        model.saveGame(game)
    }

    override suspend fun getMode(id: Int): ModeDataClass? =
        model.getMode(id)

    companion object {
        private val TAG = GameSettingsPresenterImpl::class.java.simpleName
    }

}