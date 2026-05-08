package com.goodluck.shoes.domain.usecase.product

import com.goodluck.shoes.data.models.Product
import com.goodluck.shoes.data.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(query: String): Flow<List<Product>> {
        return if (query.isBlank()) {
            productRepository.getAvailableProducts()
        } else {
            productRepository.searchProducts(query)
        }
    }
}
