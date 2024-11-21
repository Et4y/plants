package com.softxpert.plants.ui.plants_cycle.main

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.softxpert.plants.R
import com.softxpert.plants.databinding.FragmentPlantsBinding
import com.softxpert.plants.domain.model.plants.PlantModel
import com.softxpert.plants.domain.util.UiState
import com.softxpert.plants.ui.util.base.BaseFragment
import com.softxpert.plants.ui.util.toGone
import com.softxpert.plants.ui.util.toJson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlantsFragment : BaseFragment<FragmentPlantsBinding>(FragmentPlantsBinding::inflate) {

    private val viewModel by viewModels<PlantsViewModel>()


    private val plantsAdapter by lazy {
        PlantsAdapter()
    }

    private val zonesAdapter by lazy {
        ZonesAdapter()
    }


    override fun onStart() {
        super.onStart()

        initZonesRv()
        initPlantsRv()
        getOrders()
    }


    override fun handleToolbar() {
        binding.toolbar.ivBack.toGone()
        binding.toolbar.init(getString(R.string.plants))
    }

    private fun initZonesRv() {
        val zones = resources.getStringArray(R.array.zones_list).toMutableList()
        val zonesFilter = resources.getStringArray(R.array.zones_list_filter).toMutableList()

        binding.rvFilter.adapter = zonesAdapter
        zonesAdapter.submitList(zones)

        zonesAdapter.onItemClicked = { index ->
            val id = zonesFilter[index]
            if (id == "All") {
                viewModel.getData()
            } else {
                viewModel.getFilteredData(id)
            }
        }
    }

    private fun initPlantsRv() {
        binding.rvPlants.adapter = plantsAdapter
        plantsAdapter.onItemCLick = {
            val action = PlantsFragmentDirections.actionPlantsFragmentToDetailsFragment(it.toJson())
            findNavController().navigate(action)
        }
    }

    private fun getOrders() {
        lifecycleScope.launch {
            viewModel
                .uiState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    when (it) {
                        UiState.Idle -> {}
                        is UiState.Loading -> {
                            progressUtil.showProgress()
                        }

                        is UiState.Error -> {
                            progressUtil.hideProgress()
                        }

                        is UiState.Success -> {
                            progressUtil.hideProgress()
                            addDataToAdapters(it.data)
                        }
                    }

                }
        }
    }


    private fun addDataToAdapters(it: PagingData<PlantModel>) {
        plantsAdapter.submitData(lifecycle, it)
        lifecycleScope.launch {
            plantsAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.append) {
                    is LoadState.Loading -> {
                        progressUtil.showProgress()
                    }

                    is LoadState.Error -> {
                        progressUtil.hideProgress()
                    }

                    is LoadState.NotLoading -> {
                        progressUtil.hideProgress()
                    }
                }
            }
        }
    }

}