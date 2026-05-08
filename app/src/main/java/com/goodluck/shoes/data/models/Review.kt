package com.goodluck.shoes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "reviews",
    indices = [
        androidx.room.Index(value = ["productId", "customerId"], unique = true)
    ]
)
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val productId: Long,
    val customerId: Long,
    val rating: Int, // 1-5
    val title: String,
    val comment: String,
    val imageUrl: String? = null,
    val helpful: Int = 0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
