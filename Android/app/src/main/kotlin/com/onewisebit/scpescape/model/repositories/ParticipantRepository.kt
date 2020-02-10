package com.onewisebit.scpescape.model.repositories

import android.util.Log
import com.onewisebit.scpescape.model.daos.ParticipantDAO
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.entities.Role
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ParticipantRepository(private val participantDAO: ParticipantDAO) :
    InParticipantRepository {

    override suspend fun getParticipant(gameID: Long, playerID: Long): Participant =
        participantDAO.getParticipantById(gameID, playerID)

    override fun getGameParticipants(gameID: Long): Flowable<List<Participant>> =
        participantDAO.getGameParticipants(gameID)

    override fun getGameParticipantsSingle(gameID: Long): Single<List<Participant>> =
        participantDAO.getGameParticipantsSingle(gameID)

    override suspend fun getGameParticipantsBlocking(gameID: Long): List<Participant> =
        participantDAO.getGameParticipantsBlocking(gameID)

    override suspend fun setGameParticipantRole(gameID: Long, playerID: Long, roleName: String) =
        withContext(Dispatchers.IO) {
            participantDAO.setParticipantRole(
                gameID,
                playerID,
                roleName
            )
        }

    override suspend fun getCurrentParticipant(gameID: Long): Participant =
        participantDAO.getLastParticipant(gameID)

    override fun getGamePlayers(gameID: Long): Single<List<Player>> =
        participantDAO.getGamePlayers(gameID)

    override fun getGameParticipantsID(gameID: Long): Flowable<List<Long>> =
        participantDAO.getGameParticipantsID(gameID)

    override fun getParticipantState(gameID: Long, playerID: Long): Int =
        participantDAO.getParticipantState(gameID, playerID)

    override fun getParticipantNumber(gameID: Long): Single<Int> =
        participantDAO.getParticipantNumber(gameID)

    override fun getPlayers(gameID: Long): Single<List<Player>> =
        participantDAO.getGamePlayers(gameID)

    override fun getRoles(gameID: Long): Single<List<Role>> =
        participantDAO.getParticipantsRoles(gameID)

    override suspend fun getMissingParticipants(gameID: Long): List<Participant> =
        participantDAO.getLatestRoundMissingParticipants(gameID)

    override fun getParticipantRole(gameID: Long, playerID: Long): Single<Role> =
        participantDAO.getParticipantRole(gameID, playerID)

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