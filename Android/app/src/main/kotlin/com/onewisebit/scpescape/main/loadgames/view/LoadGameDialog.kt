package com.onewisebit.scpescape.main.loadgames.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.DialogLoadGameBinding
import com.onewisebit.scpescape.utilities.GAME_ID

class LoadGameDialog : DialogFragment() {

    private var _binding: DialogLoadGameBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: LoadGameListener
    private var gameID: Long = 0L

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            _binding = DialogLoadGameBinding.inflate(inflater)

            arguments?.let {args ->
                gameID = args.getLong(GAME_ID)
            }

            builder.setView(binding.root)
                .setTitle(R.string.load_game)
                .setPositiveButton( R.string.load ) { _, _ ->
                    listener.onLoadGameClick(gameID)
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as LoadGameListener
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

    interface LoadGameListener {
        fun onLoadGameClick(gameID: Long)
    }
}