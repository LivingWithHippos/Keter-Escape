package com.onewisebit.scpescape.game.pause.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.DialogPauseBinding

class PauseDialog: DialogFragment() {

    private var _binding: DialogPauseBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: PauseMenuListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            _binding = DialogPauseBinding.inflate(inflater)

            // adding these here because they don't work in onViewCreated
            binding.bCloseGame.setOnClickListener {
                listener.onCloseGameClick()
                dialog?.dismiss()
            }
            binding.bResume.setOnClickListener {
                dialog?.dismiss()
            }

            builder.setView(binding.root)
                .setTitle(R.string.pause_menu)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as PauseMenuListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement LoadGameListener")
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    interface PauseMenuListener {
        fun onCloseGameClick()
    }
}