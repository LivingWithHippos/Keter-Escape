package com.onewisebit.scpescape.playerslist.presenter

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.playerslist.PlayersContract
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class PlayersPresenterImpl(
    pView: PlayersContract.PlayersView,
    pModel: PlayersContract.PlayersModel
) : PlayersContract.PlayersPresenter {

    private var view: PlayersContract.PlayersView = pView
    private var model: PlayersContract.PlayersModel = pModel

    private val participants : MutableLiveData<List<Participant>> = MutableLiveData()
    private val players : MutableLiveData<List<Player>> = MutableLiveData()

    override fun setPlayers(gameID: Long) {
        view.initView(players, participants)

        model.getParticipantsByGame(gameID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list -> participants.postValue(list) },
                { error -> Log.d(TAG, "Error getting participants model into presenter: ", error) }
            )

        model.getAllPlayers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list -> players.postValue(list) },
                { error -> Log.d(TAG, "Error getting players model into presenter: ", error) }
            )
    }

    override fun addParticipant(gameID: Long, playerID: Long) {
        model.addGameParticipant(gameID, playerID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "Participant inserted") },
                { error -> Log.d(TAG, "Participant NOT inserted: ", error) }
            )

    }

    override fun removeParticipant(gameID: Long, playerID: Long) {
        model.removeGameParticipant(gameID, playerID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "Participant removed") },
                { error -> Log.d(TAG, "Participant NOT removed: ", error) }
            )
    }

    override fun setGameTemporary(gameID: Long, isTemp: Boolean): Completable =
        model.setTemporary(gameID, isTemp)

    override fun addRemoveParticipant(gameId: Long, playerId: Long, add: Boolean, maxParticipants: Int) {
        //TODO: methods return a completable that could be used to check for errors
        if (add) {
            if (participants.value == null ){
                Log.d(TAG, "Error getting participants data")
            }else{
                // I like living dangerously. Check if the above code control works.
                if(participants.value!!.size < maxParticipants)
                    addParticipant(gameId,playerId)
                else
                    view.tooManyParticipants()
            }
        }else{
            removeParticipant(gameId,playerId)
        }
    }


    companion object {
        private val TAG = PlayersPresenterImpl::class.java.simpleName
    }

}