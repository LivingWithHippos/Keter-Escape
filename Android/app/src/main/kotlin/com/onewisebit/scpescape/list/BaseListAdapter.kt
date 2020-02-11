package com.onewisebit.scpescape.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseVoteListAdapter(
    vararg types: Cell<RecyclerItem>,
    private val listener: AdapterListener? = null
    ) : ListAdapter<RecyclerItem, RecyclerView.ViewHolder>(
    BASE_DIFF_CALLBACK
) {

        private val cellTypes: CellTypes<RecyclerItem> =
            CellTypes(*types)

        override fun getItemViewType(position: Int): Int {
            val item = getItem(position)
            return cellTypes.of(item).type()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return cellTypes.of(viewType).holder(parent)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val item = getItem(position)
            cellTypes.of(item).bind(holder, item, listener)
        }

    }

val BASE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecyclerItem>() {

    override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return oldItem == newItem
    }

}