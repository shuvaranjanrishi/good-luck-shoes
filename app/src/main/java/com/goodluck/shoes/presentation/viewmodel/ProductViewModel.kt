package com.goodluck.shoes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodluck.shoes.data.models.Product
import com.goodluck.shoes.domain.usecase.product.GetAvailableProductsUseCase
import com.goodluck.shoes.domain.usecase.product.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductListState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAvailableProductsUseCase: GetAvailableProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _productListState = MutableStateFlow(ProductListState(isLoading = true))
    val productListState: StateFlow<ProductListState> = _productListState

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _productListState.value = ProductListState(isLoading = true)
            try {
                getAvailableProductsUseCase().collectLatest { products ->
                    _productListState.value = ProductListState(
                        isLoading = false,
                        products = products
                    )
                }
            } catch (e: Exception) {
                _productListState.value = ProductListState(
                    isLoading = false,
                    error = e.message ?: "Failed to load products"
                )
            }
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            _productListState.value = ProductListState(isLoading = true)
            try {
                searchProductsUseCase(query).collectLatest { products ->
                    _productListState.value = ProductListState(
                        isLoading = false,
                        products = products
                    )
                }
            } catch (e: Exception) {
                _productListState.value = ProductListState(
                    isLoading = false,
                    error = e.message ?: "Search failed"
                )
            }
        }
    }
}
