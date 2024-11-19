package com.softxpert.plants.ui.util.base

import androidx.recyclerview.widget.DiffUtil

class BaseDiffCallback<T : Any > : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        return oldItem !== newItem
    }


}