package com.goodluck.shoes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

enum class OrderStatus {
    PENDING,
    CONFIRMED,
    PACKED,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    RETURNED
}

enum class PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED,
    REFUNDED
}

enum class PaymentMethod {
    CASH,
    CARD,
    UPI,
    BANK_TRANSFER,
    WALLET
}

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderNumber: String, // Unique order ID like "ORD-2026-001"
    val customerId: Long,
    val sellerId: Long?,
    val status: OrderStatus = OrderStatus.PENDING,
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    val paymentMethod: PaymentMethod? = null,
    val totalAmount: Double,
    val taxAmount: Double = 0.0,
    val discountAmount: Double = 0.0,
    val shippingAddress: String,
    val orderDate: LocalDateTime = LocalDateTime.now(),
    val expectedDeliveryDate: LocalDateTime? = null,
    val actualDeliveryDate: LocalDateTime? = null,
    val notes: String = "",
    val isSyncedWithServer: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

@Entity(
    tableName = "order_items",
    indices = [
        androidx.room.Index(value = ["orderId"])
    ]
)
data class OrderItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderId: Long,
    val productId: Long,
    val size: ShoeSize,
    val quantity: Int,
    val price: Double, // Price at time of purchase
    val lineTotal: Double
)
