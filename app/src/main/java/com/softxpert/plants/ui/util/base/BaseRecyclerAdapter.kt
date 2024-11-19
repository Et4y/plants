package com.softxpert.plants.ui.util.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerAdapter<M : Any, WB : ViewBinding, VH : BaseViewHolder<M, WB>>(
    private val callback: DiffUtil.ItemCallback<M>
) : RecyclerView.Adapter<VH>() {

    private val currentList = mutableListOf<M>()

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = currentList[position]
        holder.doBindings(item)
        holder.bind()
    }

    override fun getItemViewType(position: Int): Int = position

    fun removeItem(item: M) {
        val index = currentList.indexOf(item)
        if (index != -1) {
            currentList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun removeItemByIndex(index: Int) {
        if (index in currentList.indices) {
            currentList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun addAtFirst(item: M) {
        currentList.add(0, item)
        notifyItemInserted(0)
    }

    fun addAtFirst(items: List<M>) {
        currentList.addAll(0, items)
        notifyItemRangeInserted(0, items.size)
    }

    fun addAtLast(item: M) {
        currentList.add(item)
        notifyItemInserted(currentList.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAtLast(items: List<M>, clear: Boolean = false) {
        if (clear) {
            currentList.clear()
            notifyDataSetChanged()
        }
        val start = currentList.size
        currentList.addAll(items)
        notifyItemRangeInserted(start, items.size)
    }

    fun updateItem(index: Int, newItem: M) {
        if (index in currentList.indices) {
            currentList[index] = newItem
            notifyItemChanged(index)
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(items: List<M>?) {
        currentList.clear()
        if (items != null) {
            currentList.addAll(items.distinct())
        }
        notifyDataSetChanged()
    }

    fun submitItem(item: M) {
        val index = currentList.indexOf(item)
        if (index == -1) {
            currentList.add(item)
            notifyItemInserted(currentList.size - 1)
        } else {
            updateItem(index, item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        currentList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = currentList.size
}