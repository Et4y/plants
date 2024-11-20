package com.softxpert.plants.ui.plants_cycle.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.softxpert.plants.domain.model.plants.PlantModel
import com.softxpert.plants.domain.repo.PlantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlantsViewModel @Inject constructor(private val repository: PlantsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<PlantModel>>(PagingData.empty())
    val uiState = _uiState


    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            repository.getPlants()
                .cachedIn(viewModelScope)
                .collectLatest { state ->
                    _uiState.value = state
                }
        }
    }

    fun getFilteredData(id: String) {
        viewModelScope.launch {
            repository.getFilterPlants(id)
                .cachedIn(viewModelScope)
                .collectLatest { state ->
                    _uiState.value = state
                }
        }
    }

}
