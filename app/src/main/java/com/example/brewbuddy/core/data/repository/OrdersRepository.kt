package com.example.brewbuddy.core.data.repository

import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    fun getAllOrders(): Flow<List<Order>>
    suspend fun placeOrder(items: List<OrderItem>): String
    suspend fun deleteOrder(orderId: String)
}