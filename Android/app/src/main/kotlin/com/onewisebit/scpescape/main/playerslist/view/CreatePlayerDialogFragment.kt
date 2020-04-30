package com.onewisebit.scpescape.main.playerslist.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.DialogCreatePlayerBinding

class CreatePlayerDialogFragment : DialogFragment() {

    private var _binding: DialogCreatePlayerBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: NewPlayerDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            _binding = DialogCreatePlayerBinding.inflate(inflater)


            builder.setView(binding.root)
                .setTitle(R.string.new_player)
                .setPositiveButton(
                    R.string.save
                ) { _, _ ->
                    listener.onPositiveDialogClick(binding.etNewPlayerName.text.toString())
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ ->
                    // For now do nothing on cancel
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NewPlayerDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement NewPlayerDialogListener")
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    interface NewPlayerDialogListener {
        //TODO: also pass avatar bitmap link
        fun onPositiveDialogClick(playerName: String)
    }
}