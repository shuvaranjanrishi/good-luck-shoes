package com.goodluck.shoes.di

import android.content.Context
import androidx.room.Room
import com.goodluck.shoes.data.db.GoodLuckShoesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideGoodLuckShoesDatabase(
        @ApplicationContext context: Context
    ): GoodLuckShoesDatabase {
        return Room.databaseBuilder(
            context,
            GoodLuckShoesDatabase::class.java,
            GoodLuckShoesDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: GoodLuckShoesDatabase) = database.userDao()

    @Singleton
    @Provides
    fun provideProductDao(database: GoodLuckShoesDatabase) = database.productDao()

    @Singleton
    @Provides
    fun provideOrderDao(database: GoodLuckShoesDatabase) = database.orderDao()

    @Singleton
    @Provides
    fun provideInventoryDao(database: GoodLuckShoesDatabase) = database.inventoryDao()

    @Singleton
    @Provides
    fun provideReviewDao(database: GoodLuckShoesDatabase) = database.reviewDao()

    @Singleton
    @Provides
    fun provideSyncLogDao(database: GoodLuckShoesDatabase) = database.syncLogDao()
}
