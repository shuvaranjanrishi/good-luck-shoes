package com.goodluck.shoes.data.repository

import com.goodluck.shoes.data.dao.OrderDao
import com.goodluck.shoes.data.models.Order
import com.goodluck.shoes.data.models.OrderItem
import com.goodluck.shoes.data.models.OrderStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val orderDao: OrderDao
) {
    suspend fun createOrder(order: Order, items: List<OrderItem>) {
        orderDao.insertOrderWithItems(order, items)
    }

    suspend fun updateOrder(order: Order) {
        orderDao.updateOrder(order)
    }

    suspend fun getOrderById(id: Long): Order? {
        return orderDao.getOrderById(id)
    }

    suspend fun getOrderByNumber(orderNumber: String): Order? {
        return orderDao.getOrderByNumber(orderNumber)
    }

    fun getCustomerOrders(customerId: Long): Flow<List<Order>> {
        return orderDao.getCustomerOrders(customerId)
    }

    fun getSellerOrders(sellerId: Long): Flow<List<Order>> {
        return orderDao.getSellerOrders(sellerId)
    }

    fun getOrdersByStatus(status: OrderStatus): Flow<List<Order>> {
        return orderDao.getOrdersByStatus(status)
    }

    fun getUnsyncedOrders(): Flow<List<Order>> {
        return orderDao.getUnsyncedOrders()
    }

    fun getAllOrders(): Flow<List<Order>> {
        return orderDao.getAllOrders()
    }

    suspend fun getOrderItems(orderId: Long): List<OrderItem> {
        return orderDao.getOrderItems(orderId)
    }

    suspend fun updateOrderStatus(orderId: Long, status: OrderStatus) {
        val order = orderDao.getOrderById(orderId) ?: return
        orderDao.updateOrder(order.copy(status = status))
    }
}
