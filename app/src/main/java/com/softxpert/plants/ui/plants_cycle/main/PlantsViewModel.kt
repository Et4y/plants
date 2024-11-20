package com.softxpert.plants.ui.plants_cycle.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.softxpert.plants.domain.model.plants.PlantModel
import com.softxpert.plants.domain.repo.PlantsRepository
import com.softxpert.plants.domain.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlantsViewModel @Inject constructor(private val repository: PlantsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<PagingData<PlantModel>>>(UiState.Idle)
    val uiState = _uiState


    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.Default) {
            repository.getPlants()
                .cachedIn(viewModelScope)
                .catch { exception ->
                    uiState.value = UiState.Error(exception)
                }
                .onStart {
                    uiState.value = UiState.Loading
                }
                .collect { pagingData ->
                    uiState.value = UiState.Success(pagingData)
                }
        }
    }

    fun getFilteredData(id: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.getFilterPlants(id)
                .cachedIn(viewModelScope)
                .onStart {
                    uiState.value = UiState.Loading
                }
                .catch { exception ->
                    uiState.value = UiState.Error(exception)
                }
                .collect { pagingData ->
                    uiState.value = UiState.Success(pagingData)
                }
        }
    }

}
