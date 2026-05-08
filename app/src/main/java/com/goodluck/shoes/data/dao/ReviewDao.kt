package com.goodluck.shoes.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.goodluck.shoes.data.models.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Insert
    suspend fun insertReview(review: Review): Long

    @Update
    suspend fun updateReview(review: Review)

    @Delete
    suspend fun deleteReview(review: Review)

    @Query("SELECT * FROM reviews WHERE productId = :productId ORDER BY createdAt DESC")
    fun getProductReviews(productId: Long): Flow<List<Review>>

    @Query("SELECT * FROM reviews WHERE customerId = :customerId ORDER BY createdAt DESC")
    fun getCustomerReviews(customerId: Long): Flow<List<Review>>

    @Query("SELECT AVG(rating) FROM reviews WHERE productId = :productId")
    suspend fun getProductAverageRating(productId: Long): Float?

    @Query("SELECT COUNT(*) FROM reviews WHERE productId = :productId")
    suspend fun getProductReviewCount(productId: Long): Int

    @Query("DELETE FROM reviews WHERE id = :id")
    suspend fun deleteReviewById(id: Long)
}
