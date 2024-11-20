package com.softxpert.plants.ui.plants_cycle.details

import com.softxpert.plants.R
import com.softxpert.plants.databinding.FragmentPlantDetailsBinding
import com.softxpert.plants.domain.model.plants.PlantModel
import com.softxpert.plants.ui.util.base.BaseFragment
import com.softxpert.plants.ui.util.fromJson
import com.softxpert.plants.ui.util.image_util.loadImageFromUrl
import com.softxpert.plants.ui.util.openLink
import com.softxpert.plants.ui.util.orNA
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantDetailsFragment :
    BaseFragment<FragmentPlantDetailsBinding>(FragmentPlantDetailsBinding::inflate) {


    private lateinit var plant: PlantModel

    override fun onResume() {
        super.onResume()
        addDataToView()
    }


    override fun handleToolbar() {
        binding.toolbar.init(getString(R.string.plant_details))
    }

    override fun receiveData() {
        val args = PlantDetailsFragmentArgs.fromBundle(requireArguments())
        plant = args.plant.fromJson<PlantModel>()
    }

    override fun handleClicks() {
        binding.moreDetailsButton.setOnClickListener {
            val wikipediaUrl = "https://en.wikipedia.org/wiki/${plant.scientificName.replace(" ", "_")}"
            requireActivity().openLink(wikipediaUrl)
        }
    }


    private fun addDataToView() {
        binding.plantImage.loadImageFromUrl(plant.imageUrl)
        binding.tvName.text = plant.commonName.orNA()
        binding.tvFamily.text = plant.family.orNA()
        binding.tvIndex.text = String.format("%s\n%s", plant.bibliography.orNA(), plant.scientificName.orNA())
        binding.tvAuthor.text = plant.author.orNA()
    }

}