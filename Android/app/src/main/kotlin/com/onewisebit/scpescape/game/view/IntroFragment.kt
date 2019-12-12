package com.onewisebit.scpescape.game.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.FragmentIntroBinding
import com.onewisebit.scpescape.game.IntroContract
import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Mode
import com.onewisebit.scpescape.model.repositories.RoleRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


/**
 * A simple [Fragment] subclass.
 * Use the [IntroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IntroFragment : Fragment(), IntroContract.IntroView {

    private val presenter: IntroContract.IntroPresenter by inject { parametersOf(this) }
    private lateinit var binding: FragmentIntroBinding
    private val args by navArgs<IntroFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setup(args.gameID)
    }

    @SuppressLint("CheckResult")
    override fun setupGame(game: Single<Game>, mode: Single<Mode>) {
        mode.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    binding.tvDescription.text = it.description
                    binding.tvRules.text = it.rules
                },
                { Log.d(TAG, "Mode retrieval error") }
            )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         * @return A new instance of fragment IntroFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            IntroFragment()

        private val TAG = IntroFragment::class.java.simpleName
    }
}
