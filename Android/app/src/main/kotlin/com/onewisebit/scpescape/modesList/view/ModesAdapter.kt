package com.onewisebit.scpescape.modesList.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onewisebit.scpescape.databinding.ModeListItemBinding
import com.onewisebit.scpescape.model.parsed.ModeDataClass

class ModesAdapter(
    modesList: List<ModeDataClass>,
    private val clickListener: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val modes: List<ModeDataClass> = modesList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ModeListItemBinding.inflate(layoutInflater, parent, false)
        return ModeHolder(binding)
    }

    override fun getItemCount(): Int = modes.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = modes[position]
        if (holder is ModeHolder)
            holder.bind(item, clickListener)
    }

    class ModeHolder(mBinding: ModeListItemBinding) : RecyclerView.ViewHolder(mBinding.root) {

        private val binding: ModeListItemBinding = mBinding

        fun bind(mode: ModeDataClass, clickListener: (Int) -> Unit) {
            binding.cvGameMode.setOnClickListener { clickListener(mode.id) }

            binding.tvModeDescription.text = mode.shortDescription
            binding.tvModeName.text = mode.name
            //TODO: add image
        }
    }

}