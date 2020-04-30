package com.onewisebit.scpescape.main.activity.view

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.onewisebit.scpescape.BaseSCPActivity
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.ActivityMainBinding
import com.onewisebit.scpescape.game.activity.view.GameActivity
import com.onewisebit.scpescape.main.activity.StartContract
import com.onewisebit.scpescape.main.activity.StartContract.StartView
import com.onewisebit.scpescape.main.loadgames.view.GameListFragmentDirections
import com.onewisebit.scpescape.main.loadgames.view.LoadGameDialog
import com.onewisebit.scpescape.main.playerslist.view.CreatePlayerDialogFragment
import com.onewisebit.scpescape.main.playerslist.view.ParticipantsChoiceFragmentDirections
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : BaseSCPActivity(), StartView,
    CreatePlayerDialogFragment.NewPlayerDialogListener, LoadGameDialog.LoadGameListener {

    private val presenter: StartContract.StartPresenter by inject { parametersOf(this) }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun updateTheme() {
        //TODO: update theme if changed
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onPositiveDialogClick(playerName: String) {
        presenter.addPlayer(playerName)
    }

    override fun onLoadGameClick(gameID: Long) {
        Log.d(TAG, "onLoadGameClick() called with: gameID = $gameID")
        val action = GameListFragmentDirections.loadGameToGameActivity(
                gameID,
                true
            )
        findNavController(R.id.nav_host).navigate(action)

    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
