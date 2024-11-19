package com.softxpert.plants.ui.plants_cycle.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.softxpert.plants.R
import com.softxpert.plants.databinding.ItemZonesBinding
import com.softxpert.plants.ui.util.base.BaseDiffCallback
import com.softxpert.plants.ui.util.base.BaseRecyclerAdapter
import com.softxpert.plants.ui.util.base.BaseViewHolder

class ZonesAdapter :
    BaseRecyclerAdapter<String, ItemZonesBinding, ZonesAdapter.ViewHolder>(
        BaseDiffCallback()
    ) {

    var selectedItemPosition = 0
    private var previousSelectedItemPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemZonesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    inner class ViewHolder(val binding: ItemZonesBinding) :
        BaseViewHolder<String, ItemZonesBinding>(binding) {

        override fun bind() {

            binding.tvTitle.text = getRowItem()

            binding.root.setOnClickListener {
                selectedItemPosition = bindingAdapterPosition
                notifyItemChanged(previousSelectedItemPosition)
                notifyItemChanged(selectedItemPosition)
                previousSelectedItemPosition = bindingAdapterPosition
            }

            if (selectedItemPosition == bindingAdapterPosition) {
                binding.tvTitle.setBackgroundResource(R.drawable.bg_primary)
            } else {
                binding.tvTitle.setBackgroundResource(R.drawable.bg_white_stroke_gray)
            }

        }

    }

}