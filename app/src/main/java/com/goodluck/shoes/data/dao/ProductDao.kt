package com.goodluck.shoes.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.goodluck.shoes.data.models.Product
import com.goodluck.shoes.data.models.ProductSize
import com.goodluck.shoes.data.models.ShoeType
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product: Product): Long

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Long): Product?

    @Query("SELECT * FROM products WHERE isAvailable = 1")
    fun getAvailableProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE type = :type")
    fun getProductsByType(type: ShoeType): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE brand = :brand")
    fun getProductsByBrand(brand: String): Flow<List<Product>>

    @Query("SELECT * FROM products ORDER BY rating DESC")
    fun getTopRatedProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchProducts(query: String): Flow<List<Product>>

    // Product Size queries
    @Insert
    suspend fun insertProductSize(productSize: ProductSize): Long

    @Query("SELECT * FROM product_sizes WHERE productId = :productId")
    fun getProductSizes(productId: Long): Flow<List<ProductSize>>

    @Query("SELECT quantity FROM product_sizes WHERE productId = :productId AND size = :size")
    suspend fun getProductQuantityBySize(productId: Long, size: String): Int
}
