package com.onewisebit.scpescape.fsm

import android.annotation.SuppressLint
import android.util.Log
import com.onewisebit.scpescape.model.entities.Player
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class VoteState: GameState {

    override fun handleInput(gameMachine: GameMachine, rules: HashMap<String, List<Parameter>>) {
        gameMachine.players
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {players ->
                },
                { Log.d(TAG, "Error while getting players game as permanent") }
            )

        val candidates : HashSet<Player> = HashSet()
        rules.forEach { rule ->
            when(rule.key){
                "show" -> {
                    rule.value.forEach{ parameter ->
                        when (parameter.name) {
                            /*
                            //TODO: check that the parameters are always ordered. For example using the "all" parameter as last would just re-add all the players
                            "all" -> {
                                if (parameter.getSingleBoolean())
                                    candidates.addAll()
                            }
                            "self" -> {
                                if (parameter.getSingleBoolean())
                                    if(gameMachine.currentPlayer != null)
                                        candidates.add(gameMachine.currentPlayer!!)
                                    else
                                        if(gameMachine.currentPlayer != null)
                                            candidates.remove(gameMachine.currentPlayer!!)
                            }
                            "role" -> {

                            }

                             */
                        }
                    }
                }
            }
        }

    }

    override fun updateState() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val TAG = VoteState::class.java.simpleName
    }
}