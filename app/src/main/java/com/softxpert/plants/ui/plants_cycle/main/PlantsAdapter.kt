package com.softxpert.plants.ui.plants_cycle.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.softxpert.plants.databinding.ItemPlantBinding
import com.softxpert.plants.domain.model.plants.PlantModel
import com.softxpert.plants.ui.util.base.BaseDiffCallback
import com.softxpert.plants.ui.util.base.BasePagingRecyclerAdapter
import com.softxpert.plants.ui.util.base.BaseViewHolder
import com.softxpert.plants.ui.util.image_util.loadImageFromUrl
import com.softxpert.plants.ui.util.orNA

class PlantsAdapter :
    BasePagingRecyclerAdapter<PlantModel, ItemPlantBinding, PlantsAdapter.ViewHolder>(
        BaseDiffCallback()
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPlantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    inner class ViewHolder(val binding: ItemPlantBinding) :
        BaseViewHolder<PlantModel, ItemPlantBinding>(binding) {

        override fun bind() {

            binding.ivPlant.loadImageFromUrl(getRowItem()?.imageUrl)
            binding.tvName.text = getRowItem()?.commonName.orNA()
            binding.tvYear.text = getRowItem()?.year.toString().orNA()
            binding.tvStatus.text = getRowItem()?.status.orNA()

        }

    }

}