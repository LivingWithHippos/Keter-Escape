package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.IntroContract
import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.RolesDetail
import com.onewisebit.scpescape.model.entities.Participant
import kotlin.random.Random

class IntroPresenterImpl(
    introView: IntroContract.IntroView,
    private val introModel: IntroContract.IntroModel,
    gameID: Long
) : GamePresenterImpl(introView, introModel,gameID),
    IntroContract.IntroPresenter {

    override suspend fun assignRoles() {
        //if it's a game in progress this doesn't need to be called or we can check from here directly
        val participants: List<Participant> = gameModel.getParticipants(gameID)
        val mode: ModeDataClass? = gameModel.getMode(gameID)
        if (mode != null) {
            // get roles division for this mode
            val roles: List<RolesDetail> =
                mode.rolesDivision.filter { participants.size >= it.minPlayers && participants.size <= it.maxPlayers }[0].roles
            // for simplicity we build a string array with every necessary role
            val possibleRoles: ArrayList<String> = ArrayList()
            roles.filter{!it.default}.forEach{ details ->
                for (i in 0 until details.quantity)
                    possibleRoles.add(details.role)
            }
            // special role are fixed for the players range (for now)
            // the rest of the participants will have the default role
            val defaultRole = roles.filter { it.default }[0]
            while (possibleRoles.size < participants.size)
                possibleRoles.add(defaultRole.role)

            // assigning the various roles in the db
            participants.forEach {
                val roleName = possibleRoles.removeAt(Random.nextInt(possibleRoles.size))
                introModel.assignRole(gameID, it.playerID, roleName)
            }

        }
        else
            throw IllegalArgumentException("GamePresenter couldn't get the mode from game $gameID to assign roles")
    }

}