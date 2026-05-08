package com.goodluck.shoes.domain.usecase.order

import com.goodluck.shoes.data.models.Order
import com.goodluck.shoes.data.models.OrderItem
import com.goodluck.shoes.data.models.OrderStatus
import com.goodluck.shoes.data.models.PaymentMethod
import com.goodluck.shoes.data.models.PaymentStatus
import com.goodluck.shoes.data.repository.OrderRepository
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(
        customerId: Long,
        items: List<OrderItem>,
        shippingAddress: String,
        paymentMethod: PaymentMethod
    ): Result<String> = try {
        require(items.isNotEmpty()) { "Order must have at least one item" }
        require(shippingAddress.isNotBlank()) { "Shipping address required" }

        val totalAmount = items.sumOf { it.lineTotal }
        val orderNumber = generateOrderNumber()

        val order = Order(
            orderNumber = orderNumber,
            customerId = customerId,
            totalAmount = totalAmount,
            shippingAddress = shippingAddress,
            paymentMethod = paymentMethod,
            status = OrderStatus.PENDING,
            paymentStatus = PaymentStatus.PENDING,
            expectedDeliveryDate = LocalDateTime.now().plusDays(7)
        )

        orderRepository.createOrder(order, items)
        Result.success(orderNumber)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun generateOrderNumber(): String {
        val timestamp = System.currentTimeMillis() / 1000
        val random = UUID.randomUUID().toString().take(8).uppercase()
        return "ORD-$timestamp-$random"
    }
}
