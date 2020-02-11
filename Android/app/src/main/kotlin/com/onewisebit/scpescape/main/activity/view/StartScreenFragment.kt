package com.onewisebit.scpescape.main.activity.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.onewisebit.scpescape.databinding.FragmentStartScreenBinding

/**
 * A simple [Fragment] subclass.
 * Use the [StartScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartScreenFragment : Fragment() {

    private var _binding: FragmentStartScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bAbout.setOnClickListener {
            val action =
                StartScreenFragmentDirections.actionStartToAbout()
            view.findNavController().navigate(action)
        }
        binding.bSettings.setOnClickListener {
            val action =
                StartScreenFragmentDirections.actionStartToSettings()
            view.findNavController().navigate(action)
        }
        binding.bNewGame.setOnClickListener {
            val action =
                StartScreenFragmentDirections.actionStartToNewGame()
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment StartScreenFragment.
         */
        @JvmStatic
        fun newInstance() =
            StartScreenFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
