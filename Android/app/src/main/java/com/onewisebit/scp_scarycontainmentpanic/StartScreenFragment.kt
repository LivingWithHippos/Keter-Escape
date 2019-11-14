package com.onewisebit.scp_scarycontainmentpanic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.onewisebit.scp_scarycontainmentpanic.databinding.FragmentStartScreenBinding

/**
 * A simple [Fragment] subclass.
 * Use the [StartScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartScreenFragment : Fragment() {

    private lateinit var binding: FragmentStartScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartScreenBinding.inflate(layoutInflater)
        return binding.root
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
