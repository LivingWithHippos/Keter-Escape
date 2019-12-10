package com.onewisebit.scp_scarycontainmentpanic.model

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ParticipantRepository(private val participantDAO: ParticipantDAO) : InParticipantRepository {

    override fun getParticipant(gameID: Long, playerID: Long): Participant =
        participantDAO.getParticipantById(gameID, playerID)

    override fun getGameParticipants(gameID: Long): Flowable<List<Participant>> =
        participantDAO.getGameParticipantList(gameID)

    override fun getGameParticipantsID(gameID: Long): Flowable<List<Long>> =
        participantDAO.getGameParticipantsID(gameID)

    override fun getParticipantState(gameID: Long, playerID: Long): Int =
        participantDAO.getParticipantState(gameID, playerID)

    override fun insertParticipant(participant: Participant): Completable {
        return participantDAO.insertParticipant(participant)
    }

    override fun updateParticipant(participant: Participant) {
        participantDAO.updateParticipant(participant)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "Update Success") },
                { Log.d(TAG, "Update Error") }
            )
    }

    override fun removeParticipant(participant: Participant) =
        participantDAO.removeParticipant(participant)

    override fun removeParticipant(gameID: Long, playerID: Long) =
        participantDAO.removeParticipant(gameID, playerID)

    override fun removeAllParticipant() {
        Completable.fromAction { participantDAO.deleteAllParticipants() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "Delete all Success") },
                { Log.d(TAG, "Delete all Error") }
            )
    }

    companion object {
        private val TAG = PlayerRepository::class.java.simpleName
    }
}