package com.goodluck.shoes.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.goodluck.shoes.data.models.Inventory
import com.goodluck.shoes.data.models.InventoryBySize
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Insert
    suspend fun insertInventory(inventory: Inventory): Long

    @Update
    suspend fun updateInventory(inventory: Inventory)

    @Delete
    suspend fun deleteInventory(inventory: Inventory)

    @Query("SELECT * FROM inventory WHERE sellerId = :sellerId")
    fun getSellerInventory(sellerId: Long): Flow<List<Inventory>>

    @Query("SELECT * FROM inventory WHERE sellerId = :sellerId AND productId = :productId")
    suspend fun getInventoryBySellerAndProduct(sellerId: Long, productId: Long): Inventory?

    @Query("SELECT * FROM inventory WHERE productId = :productId")
    fun getProductInventory(productId: Long): Flow<List<Inventory>>

    @Query("SELECT * FROM inventory WHERE quantity <= reorderLevel ORDER BY quantity ASC")
    fun getLowStockItems(): Flow<List<Inventory>>

    @Query("UPDATE inventory SET quantity = quantity - :amount WHERE id = :inventoryId")
    suspend fun decreaseStock(inventoryId: Long, amount: Int)

    @Query("UPDATE inventory SET quantity = quantity + :amount WHERE id = :inventoryId")
    suspend fun increaseStock(inventoryId: Long, amount: Int)

    // Inventory by Size queries
    @Insert
    suspend fun insertInventoryBySize(inventoryBySize: InventoryBySize): Long

    @Update
    suspend fun updateInventoryBySize(inventoryBySize: InventoryBySize)

    @Query("SELECT * FROM inventory_by_size WHERE inventoryId = :inventoryId")
    fun getInventorySizeDetails(inventoryId: Long): Flow<List<InventoryBySize>>

    @Query("SELECT * FROM inventory_by_size WHERE inventoryId = :inventoryId AND size = :size")
    suspend fun getInventorySizeQuantity(inventoryId: Long, size: String): InventoryBySize?

    @Query("UPDATE inventory_by_size SET quantity = quantity - :amount WHERE inventoryId = :inventoryId AND size = :size")
    suspend fun decreaseStockBySize(inventoryId: Long, size: String, amount: Int)
}
