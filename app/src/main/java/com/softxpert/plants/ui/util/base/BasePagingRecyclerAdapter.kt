package com.softxpert.plants.ui.util.base

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

abstract class BasePagingRecyclerAdapter<M : Any, WB : ViewBinding, VH : BaseViewHolder<M, WB>>
    (callback: DiffUtil.ItemCallback<M>) :
    PagingDataAdapter<M, VH>(callback) {

    var onItemCLick :((M) -> Unit) ?=null


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.doBindings((getItem(position)))

        holder.bind()


        holder.itemView.setOnClickListener {
            onItemCLick?.invoke(getItem(position)!!)
        }
    }




}