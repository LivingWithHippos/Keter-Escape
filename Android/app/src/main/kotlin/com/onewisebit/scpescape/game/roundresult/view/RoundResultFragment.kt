package com.onewisebit.scpescape.game.roundresult.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.game.BaseGameFragment

/**
 * A simple [Fragment] subclass.
 */
class RoundResultFragment (gameID: Long, private val onActionListener: (action: Action) -> Unit) :
    BaseGameFragment(gameID, onActionListener) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_round_result, container, false)
    }

}
