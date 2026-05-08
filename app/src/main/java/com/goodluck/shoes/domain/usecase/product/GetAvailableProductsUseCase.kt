package com.goodluck.shoes.domain.usecase.product

import com.goodluck.shoes.data.models.Product
import com.goodluck.shoes.data.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAvailableProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return productRepository.getAvailableProducts()
    }
}
