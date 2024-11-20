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

    private var selectedItemPosition = 0
    private var previousSelectedItemPosition = 0
    var onItemClicked: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemZonesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    inner class ViewHolder(private val binding: ItemZonesBinding) :
        BaseViewHolder<String, ItemZonesBinding>(binding) {

        override fun bind() {

            binding.tvTitle.text = getRowItem()

            binding.root.setOnClickListener {
                selectedItemPosition = bindingAdapterPosition
                notifyItemChanged(previousSelectedItemPosition)
                notifyItemChanged(selectedItemPosition)
                previousSelectedItemPosition = bindingAdapterPosition
                onItemClicked?.invoke(selectedItemPosition)
            }

            if (selectedItemPosition == bindingAdapterPosition) {
                binding.container.setBackgroundResource(R.drawable.bg_primary)
            } else {
                binding.container.setBackgroundResource(R.drawable.bg_white_stroke_gray)
            }

        }

    }

}