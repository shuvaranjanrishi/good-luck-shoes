package com.goodluck.shoes.data.repository

import com.goodluck.shoes.data.dao.InventoryDao
import com.goodluck.shoes.data.models.Inventory
import com.goodluck.shoes.data.models.InventoryBySize
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryRepository @Inject constructor(
    private val inventoryDao: InventoryDao
) {
    suspend fun addInventory(inventory: Inventory): Long {
        return inventoryDao.insertInventory(inventory)
    }

    suspend fun updateInventory(inventory: Inventory) {
        inventoryDao.updateInventory(inventory)
    }

    suspend fun deleteInventory(inventory: Inventory) {
        inventoryDao.deleteInventory(inventory)
    }

    fun getSellerInventory(sellerId: Long): Flow<List<Inventory>> {
        return inventoryDao.getSellerInventory(sellerId)
    }

    suspend fun getInventoryBySellerAndProduct(sellerId: Long, productId: Long): Inventory? {
        return inventoryDao.getInventoryBySellerAndProduct(sellerId, productId)
    }

    fun getProductInventory(productId: Long): Flow<List<Inventory>> {
        return inventoryDao.getProductInventory(productId)
    }

    fun getLowStockItems(): Flow<List<Inventory>> {
        return inventoryDao.getLowStockItems()
    }

    suspend fun decreaseStock(inventoryId: Long, amount: Int) {
        inventoryDao.decreaseStock(inventoryId, amount)
    }

    suspend fun increaseStock(inventoryId: Long, amount: Int) {
        inventoryDao.increaseStock(inventoryId, amount)
    }

    suspend fun addInventoryBySize(inventoryBySize: InventoryBySize): Long {
        return inventoryDao.insertInventoryBySize(inventoryBySize)
    }

    suspend fun updateInventoryBySize(inventoryBySize: InventoryBySize) {
        inventoryDao.updateInventoryBySize(inventoryBySize)
    }

    fun getInventorySizeDetails(inventoryId: Long): Flow<List<InventoryBySize>> {
        return inventoryDao.getInventorySizeDetails(inventoryId)
    }
}
