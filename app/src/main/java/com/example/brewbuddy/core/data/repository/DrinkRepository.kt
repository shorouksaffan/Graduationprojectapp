package com.example.brewbuddy.core.data.repository

import com.example.brewbuddy.core.data.remote.Result
import com.example.brewbuddy.core.model.Drink
import kotlinx.coroutines.flow.Flow

interface DrinkRepository {
    fun getHotDrinks(): Flow<Result<List<Drink>>>
    fun getColdDrinks(): Flow<Result<List<Drink>>>
    fun getAllDrinks(): Flow<Result<List<Drink>>>
    fun searchDrinks(query: String): Flow<List<Drink>>
    suspend fun getDrinkById(drinkId: Int): Drink?
    suspend fun refreshDrinks(): Result<Unit>
}
