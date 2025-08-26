package com.example.brewbuddy.core.data.repository.impl

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.brewbuddy.core.data.local.dao.DrinkCacheDao
import com.example.brewbuddy.core.data.remote.ApiManager
import com.example.brewbuddy.core.data.remote.CoffeeApiService
import com.example.brewbuddy.core.data.remote.Result
import com.example.brewbuddy.core.data.remote.dto.toDrinkCacheEntity
import com.example.brewbuddy.core.data.repository.DrinkRepository
import com.example.brewbuddy.core.data.repository.toDrink
import com.example.brewbuddy.core.model.Drink
import com.example.mygraduationapp.core.util.DispatchersProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrinkRepositoryImpl @Inject constructor(
    private val apiService: CoffeeApiService,
    private val drinkCacheDao: DrinkCacheDao,
    private val dispatchersProvider: DispatchersProvider
) : DrinkRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getHotDrinks(): Flow<Result<List<Drink>>> = flow {
        // Return cached data first
        val cachedDrinks = drinkCacheDao.getDrinksByType(isHot = true)
            .map { entities -> entities.map { it.toDrink() } }
            .first()

        if (cachedDrinks.isNotEmpty()) {
            emit(Result.Success(cachedDrinks))
        } else {
            // If no cached data, fetch from API
            val apiResult = ApiManager.execute {
                apiService.getHotCoffees()
            }

            when (apiResult) {
                is Result.Success -> {
                    val entities = apiResult.data.map { it.toDrinkCacheEntity(isHot = true) }
                    drinkCacheDao.insertDrinks(entities)
                    emit(Result.Success(entities.map { it.toDrink() }))
                }

                is Result.Failure -> {
                    emit(Result.Failure(apiResult.exception))
                }
            }
        }
    }.flowOn(dispatchersProvider.io)

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getColdDrinks(): Flow<Result<List<Drink>>> = flow {
        // Return cached data first
        val cachedDrinks = drinkCacheDao.getDrinksByType(isHot = false)
            .map { entities -> entities.map { it.toDrink() } }
            .first()

        if (cachedDrinks.isNotEmpty()) {
            emit(Result.Success(cachedDrinks))
        } else {
            // If no cached data, fetch from API
            val apiResult = ApiManager.execute {
                apiService.getIcedCoffees()
            }

            when (apiResult) {
                is Result.Success -> {
                    val entities = apiResult.data.map { it.toDrinkCacheEntity(isHot = false) }
                    drinkCacheDao.insertDrinks(entities)
                    emit(Result.Success(entities.map { it.toDrink() }))
                }

                is Result.Failure -> {
                    emit(Result.Failure(apiResult.exception))
                }
            }
        }
    }.flowOn(dispatchersProvider.io)

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getAllDrinks(): Flow<Result<List<Drink>>> = flow {
        val cachedDrinks = drinkCacheDao.getAllDrinks().first()

        if (cachedDrinks.isNotEmpty()) {
            emit(Result.Success(cachedDrinks.map { it.toDrink() }))
        } else {
            // If cache is empty, refresh and then emit the result
            val refreshResult = refreshDrinksInternal()
            when (refreshResult) {
                is Result.Success -> {
                    val freshDrinks = drinkCacheDao.getAllDrinks().first()
                    emit(Result.Success(freshDrinks.map { it.toDrink() }))
                }

                is Result.Failure -> {
                    emit(refreshResult)
                }
            }
        }
    }.flowOn(dispatchersProvider.io)

    override fun searchDrinks(query: String): Flow<List<Drink>> {
        return drinkCacheDao.searchDrinks(query)
            .map { entities -> entities.map { it.toDrink() } }
            .flowOn(dispatchersProvider.io)
    }

    override suspend fun getDrinkById(drinkId: Int): Drink? {
        return withContext(dispatchersProvider.io) {
            drinkCacheDao.getDrinkById(drinkId)?.toDrink()
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun refreshDrinks(): Result<Unit> {
        return withContext(dispatchersProvider.io) {
            refreshDrinksInternal()
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private suspend fun refreshDrinksInternal(): Result<Unit> {
        // Fetch both hot and cold drinks
        val hotResult = ApiManager.execute { apiService.getHotCoffees() }
        val coldResult = ApiManager.execute { apiService.getIcedCoffees() }

        return when {
            hotResult is Result.Success &&
                    coldResult is Result.Success -> {
                val hotEntities = hotResult.data.map { it.toDrinkCacheEntity(isHot = true) }
                val coldEntities = coldResult.data.map { it.toDrinkCacheEntity(isHot = false) }

                // Clear existing data and insert fresh data
                drinkCacheDao.clearAll()
                drinkCacheDao.insertDrinks(hotEntities + coldEntities)

                Result.Success(Unit)
            }

            hotResult is Result.Failure -> {
                Result.Failure(hotResult.exception)
            }

            coldResult is Result.Failure -> {
                Result.Failure(coldResult.exception)
            }

            else -> {
                Result.Failure(Exception("Unexpected refresh result"))
            }
        }
    }
}