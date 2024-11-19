package com.softxpert.plants.ui.util.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<MODEL, VIEW_BINDING : ViewBinding> constructor(viewBinding: VIEW_BINDING) :
    RecyclerView.ViewHolder(viewBinding.root) {

    private var item: MODEL? = null

    fun doBindings(data: MODEL?) {
        this.item = data
    }

    abstract fun bind()
    open fun bind(position: Int){}

    fun getRowItem(): MODEL? {
        return item
    }
}