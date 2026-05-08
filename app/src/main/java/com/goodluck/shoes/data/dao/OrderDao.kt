package com.goodluck.shoes.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.goodluck.shoes.data.models.Order
import com.goodluck.shoes.data.models.OrderItem
import com.goodluck.shoes.data.models.OrderStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert
    suspend fun insertOrder(order: Order): Long

    @Update
    suspend fun updateOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Query("SELECT * FROM orders WHERE id = :id")
    suspend fun getOrderById(id: Long): Order?

    @Query("SELECT * FROM orders WHERE orderNumber = :orderNumber")
    suspend fun getOrderByNumber(orderNumber: String): Order?

    @Query("SELECT * FROM orders WHERE customerId = :customerId ORDER BY orderDate DESC")
    fun getCustomerOrders(customerId: Long): Flow<List<Order>>

    @Query("SELECT * FROM orders WHERE sellerId = :sellerId ORDER BY orderDate DESC")
    fun getSellerOrders(sellerId: Long): Flow<List<Order>>

    @Query("SELECT * FROM orders WHERE status = :status ORDER BY orderDate DESC")
    fun getOrdersByStatus(status: OrderStatus): Flow<List<Order>>

    @Query("SELECT * FROM orders WHERE isSyncedWithServer = 0")
    fun getUnsyncedOrders(): Flow<List<Order>>

    @Query("SELECT * FROM orders ORDER BY orderDate DESC")
    fun getAllOrders(): Flow<List<Order>>

    // Order Item queries
    @Insert
    suspend fun insertOrderItem(orderItem: OrderItem): Long

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    suspend fun getOrderItems(orderId: Long): List<OrderItem>

    @Transaction
    suspend fun insertOrderWithItems(order: Order, items: List<OrderItem>) {
        val orderId = insertOrder(order)
        items.forEach { item ->
            insertOrderItem(item.copy(orderId = orderId))
        }
    }
}
