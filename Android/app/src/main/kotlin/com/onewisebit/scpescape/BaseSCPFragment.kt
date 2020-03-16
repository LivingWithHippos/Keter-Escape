package com.onewisebit.scpescape

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseSCPFragment<T : ViewBinding> : Fragment()
{
    var _binding: T? = null
    val binding get() = _binding!!

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}