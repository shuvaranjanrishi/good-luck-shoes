package com.goodluck.shoes.data.repository

import com.goodluck.shoes.data.dao.ReviewDao
import com.goodluck.shoes.data.models.Review
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepository @Inject constructor(
    private val reviewDao: ReviewDao
) {
    suspend fun addReview(review: Review): Long {
        return reviewDao.insertReview(review)
    }

    suspend fun updateReview(review: Review) {
        reviewDao.updateReview(review)
    }

    suspend fun deleteReview(review: Review) {
        reviewDao.deleteReview(review)
    }

    fun getProductReviews(productId: Long): Flow<List<Review>> {
        return reviewDao.getProductReviews(productId)
    }

    fun getCustomerReviews(customerId: Long): Flow<List<Review>> {
        return reviewDao.getCustomerReviews(customerId)
    }

    suspend fun getProductAverageRating(productId: Long): Float? {
        return reviewDao.getProductAverageRating(productId)
    }

    suspend fun getProductReviewCount(productId: Long): Int {
        return reviewDao.getProductReviewCount(productId)
    }
}
