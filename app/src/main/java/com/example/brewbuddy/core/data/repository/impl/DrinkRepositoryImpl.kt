package com.example.brewbuddy.core.data.repository.impl

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.brewbuddy.core.data.remote.ApiManager
import com.example.brewbuddy.core.data.remote.CoffeeApiService
import com.example.brewbuddy.core.data.repository.DrinkRepository
import com.example.brewbuddy.core.model.Drink
import com.example.brewbuddy.core.data.remote.Result
import com.example.brewbuddy.core.data.remote.dto.toDrink
import kotlinx.coroutines.flow.Flow

class DrinkRepositoryImpl(private val apiManager: ApiManager, private val coffeeApiService: CoffeeApiService) : DrinkRepository {
    override fun getHotDrinks(): Flow<Result<List<Drink>>> {
        return apiManager.execute {
            coffeeApiService.getHotCoffees().map { it.toDrink(isHot = true) }
        }
    }

    override fun getColdDrinks(): Flow<Result<List<Drink>>> {
        TODO("Not yet implemented")
    }

    override fun getAllDrinks(): Flow<Result<List<Drink>>> {
        TODO("Not yet implemented")
    }

    override fun searchDrinks(query: String): Flow<List<Drink>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDrinkById(drinkId: Int): Drink? {
        TODO("Not yet implemented")
    }

    override suspend fun refreshDrinks(): Result<Unit> {
        TODO("Not yet implemented")
    }
}
