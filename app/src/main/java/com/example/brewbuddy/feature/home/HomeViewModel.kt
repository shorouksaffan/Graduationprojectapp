package com.example.brewbuddy.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewbuddy.core.data.remote.Result
import com.example.brewbuddy.core.data.repository.DrinkRepository
import com.example.brewbuddy.core.model.Drink
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: DrinkRepository
) : ViewModel() {

    private val _hotDrinks = MutableStateFlow<Result<List<Drink>>>(Result.Loading)
    val hotDrinks: StateFlow<Result<List<Drink>>> = _hotDrinks.asStateFlow()

    private val _coldDrinks = MutableStateFlow<Result<List<Drink>>>(Result.Loading)
    val coldDrinks: StateFlow<Result<List<Drink>>> = _coldDrinks.asStateFlow()

    private val _allDrinks = MutableStateFlow<Result<List<Drink>>>(Result.Loading)
    val allDrinks: StateFlow<Result<List<Drink>>> = _allDrinks.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Drink>>(emptyList())
    val searchResults: StateFlow<List<Drink>> = _searchResults.asStateFlow()

    init {
        loadHotDrinks()
        loadColdDrinks()
        loadAllDrinks()
    }

    private fun loadHotDrinks() {
        viewModelScope.launch {
            repository.getHotDrinks()
                .collect { result -> _hotDrinks.value = result }
        }
    }

    private fun loadColdDrinks() {
        viewModelScope.launch {
            repository.getColdDrinks()
                .collect { result -> _coldDrinks.value = result }
        }
    }

    private fun loadAllDrinks() {
        viewModelScope.launch {
            repository.getAllDrinks()
                .collect { result -> _allDrinks.value = result }
        }
    }

    fun searchDrinks(query: String) {
        viewModelScope.launch {
            repository.searchDrinks(query)
                .collect { drinks -> _searchResults.value = drinks }
        }
    }

    fun refreshDrinks() {
        viewModelScope.launch {
            repository.refreshDrinks()
            // After refresh, reload data
            loadHotDrinks()
            loadColdDrinks()
            loadAllDrinks()
        }
    }
}
