package com.goodluck.shoes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

// Tracks inventory for each seller
@Entity(
    tableName = "inventory",
    indices = [
        androidx.room.Index(value = ["sellerId", "productId"], unique = true)
    ]
)
data class Inventory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sellerId: Long,
    val productId: Long,
    val quantity: Int = 0,
    val reorderLevel: Int = 5,
    val lastRestockDate: LocalDateTime? = null,
    val costPrice: Double = 0.0,
    val sellingPrice: Double = 0.0,
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

// Tracks size-wise inventory for each seller
@Entity(
    tableName = "inventory_by_size",
    indices = [
        androidx.room.Index(value = ["inventoryId", "size"], unique = true)
    ]
)
data class InventoryBySize(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val inventoryId: Long,
    val size: ShoeSize,
    val quantity: Int = 0,
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
