package com.onewisebit.scpescape.main.loadgames.presenter

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.onewisebit.scpescape.main.loadgames.GamesListContract
import com.onewisebit.scpescape.model.entities.Game
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class GamesListPresenterImpl(
    gView: GamesListContract.GamesListView,
    gModel: GamesListContract.GamesListModel
): GamesListContract.GamesListPresenter {

    private var view: GamesListContract.GamesListView = gView
    private var model: GamesListContract.GamesListModel = gModel

    private val games: MutableLiveData<List<Game>> = MutableLiveData()

    override fun initializeGamesList() {
        view.initView(games)

        model.getUnfinishedGamesList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list -> games.postValue(list) },
                { error -> Log.d(TAG, "Error getting the list of unfinished games: ", error) }
            )
    }

    override fun loadGame(gameID: Long) {
        TODO("Not yet implemented")
    }

    override fun deleteGame(gameID: Long) {
        model.deleteGameByID(gameID)
    }

    companion object {
        private val TAG = GamesListPresenterImpl::class.java.simpleName
    }
}