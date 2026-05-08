package com.goodluck.shoes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

enum class ShoeSize {
    SIZE_5, SIZE_6, SIZE_7, SIZE_8, SIZE_9, SIZE_10, SIZE_11, SIZE_12, SIZE_13
}

enum class ShoeType {
    SNEAKERS,
    CASUAL,
    FORMAL,
    SPORTS,
    SANDALS,
    BOOTS,
    HEELS,
    SLIPPERS
}

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val price: Double,
    val brand: String,
    val type: ShoeType,
    val color: String,
    val imageUrl: String? = null,
    val rating: Float = 0f,
    val totalRatings: Int = 0,
    val isAvailable: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

// Stores product size variants
@Entity(tableName = "product_sizes")
data class ProductSize(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val productId: Long,
    val size: ShoeSize,
    val quantity: Int = 0
)
