package com.goodluck.shoes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.goodluck.shoes.data.dao.InventoryDao
import com.goodluck.shoes.data.dao.OrderDao
import com.goodluck.shoes.data.dao.ProductDao
import com.goodluck.shoes.data.dao.ReviewDao
import com.goodluck.shoes.data.dao.SyncLogDao
import com.goodluck.shoes.data.dao.UserDao
import com.goodluck.shoes.data.models.Inventory
import com.goodluck.shoes.data.models.InventoryBySize
import com.goodluck.shoes.data.models.Order
import com.goodluck.shoes.data.models.OrderItem
import com.goodluck.shoes.data.models.Product
import com.goodluck.shoes.data.models.ProductSize
import com.goodluck.shoes.data.models.Review
import com.goodluck.shoes.data.models.SyncLog
import com.goodluck.shoes.data.models.User
import com.goodluck.shoes.data.util.Converters

@Database(
    entities = [
        User::class,
        Product::class,
        ProductSize::class,
        Inventory::class,
        InventoryBySize::class,
        Order::class,
        OrderItem::class,
        Review::class,
        SyncLog::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class GoodLuckShoesDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun reviewDao(): ReviewDao
    abstract fun syncLogDao(): SyncLogDao

    companion object {
        const val DATABASE_NAME = "good_luck_shoes.db"
    }
}
