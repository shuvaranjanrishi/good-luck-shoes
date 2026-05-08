package com.goodluck.shoes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodluck.shoes.data.models.Order
import com.goodluck.shoes.data.models.OrderItem
import com.goodluck.shoes.data.models.OrderStatus
import com.goodluck.shoes.data.models.PaymentMethod
import com.goodluck.shoes.data.models.Product
import com.goodluck.shoes.data.models.ShoeSize
import com.goodluck.shoes.domain.usecase.order.CreateOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartItem(
    val product: Product,
    val size: ShoeSize,
    val quantity: Int
)

data class CartState(
    val items: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val totalItems: Int = 0
)

data class CheckoutState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val orderNumber: String? = null,
    val error: String? = null
)

@HiltViewModel
class CartViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase
) : ViewModel() {

    private val _cartState = MutableStateFlow(CartState())
    val cartState: StateFlow<CartState> = _cartState

    private val _checkoutState = MutableStateFlow(CheckoutState())
    val checkoutState: StateFlow<CheckoutState> = _checkoutState

    fun addToCart(product: Product, size: ShoeSize, quantity: Int = 1) {
        val currentItems = _cartState.value.items.toMutableList()
        val existingItem = currentItems.find { 
            it.product.id == product.id && it.size == size 
        }

        if (existingItem != null) {
            currentItems[currentItems.indexOf(existingItem)] = 
                existingItem.copy(quantity = existingItem.quantity + quantity)
        } else {
            currentItems.add(CartItem(product, size, quantity))
        }

        updateCartState(currentItems)
    }

    fun removeFromCart(product: Product, size: ShoeSize) {
        val currentItems = _cartState.value.items
            .filter { !(it.product.id == product.id && it.size == size) }
        updateCartState(currentItems)
    }

    fun updateQuantity(product: Product, size: ShoeSize, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(product, size)
        } else {
            val currentItems = _cartState.value.items.map { item ->
                if (item.product.id == product.id && item.size == size) {
                    item.copy(quantity = quantity)
                } else {
                    item
                }
            }
            updateCartState(currentItems)
        }
    }

    fun clearCart() {
        _cartState.value = CartState()
    }

    fun checkout(
        customerId: Long,
        shippingAddress: String,
        paymentMethod: PaymentMethod
    ) {
        viewModelScope.launch {
            _checkoutState.value = CheckoutState(isLoading = true)

            val items = _cartState.value.items.map { cartItem ->
                OrderItem(
                    productId = cartItem.product.id,
                    size = cartItem.size,
                    quantity = cartItem.quantity,
                    price = cartItem.product.price,
                    lineTotal = cartItem.product.price * cartItem.quantity
                )
            }

            createOrderUseCase(
                customerId = customerId,
                items = items,
                shippingAddress = shippingAddress,
                paymentMethod = paymentMethod
            ).onSuccess { orderNumber ->
                clearCart()
                _checkoutState.value = CheckoutState(
                    isLoading = false,
                    success = true,
                    orderNumber = orderNumber
                )
            }.onFailure { error ->
                _checkoutState.value = CheckoutState(
                    isLoading = false,
                    error = error.message ?: "Checkout failed"
                )
            }
        }
    }

    private fun updateCartState(items: List<CartItem>) {
        val totalPrice = items.sumOf { it.product.price * it.quantity }
        val totalItems = items.sumOf { it.quantity }
        _cartState.value = CartState(
            items = items,
            totalPrice = totalPrice,
            totalItems = totalItems
        )
    }
}
