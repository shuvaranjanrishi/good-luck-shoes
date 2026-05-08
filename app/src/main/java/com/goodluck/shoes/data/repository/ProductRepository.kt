package com.goodluck.shoes.data.repository

import com.goodluck.shoes.data.dao.ProductDao
import com.goodluck.shoes.data.models.Product
import com.goodluck.shoes.data.models.ProductSize
import com.goodluck.shoes.data.models.ShoeType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productDao: ProductDao
) {
    suspend fun addProduct(product: Product): Long {
        return productDao.insertProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    suspend fun getProductById(id: Long): Product? {
        return productDao.getProductById(id)
    }

    fun getAvailableProducts(): Flow<List<Product>> {
        return productDao.getAvailableProducts()
    }

    fun getProductsByType(type: ShoeType): Flow<List<Product>> {
        return productDao.getProductsByType(type)
    }

    fun getProductsByBrand(brand: String): Flow<List<Product>> {
        return productDao.getProductsByBrand(brand)
    }

    fun getTopRatedProducts(): Flow<List<Product>> {
        return productDao.getTopRatedProducts()
    }

    fun searchProducts(query: String): Flow<List<Product>> {
        return productDao.searchProducts(query)
    }

    suspend fun addProductSize(productSize: ProductSize): Long {
        return productDao.insertProductSize(productSize)
    }

    fun getProductSizes(productId: Long): Flow<List<ProductSize>> {
        return productDao.getProductSizes(productId)
    }
}
