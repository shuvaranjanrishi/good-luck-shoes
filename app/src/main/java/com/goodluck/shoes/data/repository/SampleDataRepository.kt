package com.goodluck.shoes.data.repository

import com.goodluck.shoes.data.dao.ProductDao
import com.goodluck.shoes.data.models.Product
import com.goodluck.shoes.data.models.ProductSize
import com.goodluck.shoes.data.models.ShoeSize
import com.goodluck.shoes.data.models.ShoeType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleDataRepository @Inject constructor(
    private val productDao: ProductDao
) {
    suspend fun populateSampleProducts() = withContext(Dispatchers.IO) {
        val sampleProducts = listOf(
            Product(
                name = "Nike Air Max 270",
                description = "Comfortable running shoes with great cushioning",
                price = 8999.0,
                brand = "Nike",
                type = ShoeType.SNEAKERS,
                color = "Black/White",
                rating = 4.5f,
                totalRatings = 156
            ),
            Product(
                name = "Adidas Ultraboost 21",
                description = "High performance boost technology",
                price = 9999.0,
                brand = "Adidas",
                type = ShoeType.SPORTS,
                color = "White/Blue",
                rating = 4.7f,
                totalRatings = 234
            ),
            Product(
                name = "Puma RS-X",
                description = "Retro style with modern comfort",
                price = 6499.0,
                brand = "Puma",
                type = ShoeType.CASUAL,
                color = "Red/Black",
                rating = 4.3f,
                totalRatings = 89
            ),
            Product(
                name = "Skechers Go Walk",
                description = "Perfect for daily walks",
                price = 4999.0,
                brand = "Skechers",
                type = ShoeType.CASUAL,
                color = "Gray",
                rating = 4.2f,
                totalRatings = 412
            ),
            Product(
                name = "Woodland Boots",
                description = "Durable outdoor boots",
                price = 5999.0,
                brand = "Woodland",
                type = ShoeType.BOOTS,
                color = "Brown",
                rating = 4.4f,
                totalRatings = 178
            ),
            Product(
                name = "Crocs Comfort",
                description = "Ultra comfortable casual shoes",
                price = 2999.0,
                brand = "Crocs",
                type = ShoeType.SLIPPERS,
                color = "Blue",
                rating = 4.1f,
                totalRatings = 567
            ),
            Product(
                name = "Reebok Classic Leather",
                description = "Timeless classic design",
                price = 5499.0,
                brand = "Reebok",
                type = ShoeType.CASUAL,
                color = "White",
                rating = 4.3f,
                totalRatings = 234
            ),
            Product(
                name = "New Balance 574",
                description = "Iconic comfort and style",
                price = 7999.0,
                brand = "New Balance",
                type = ShoeType.SNEAKERS,
                color = "Gray/White",
                rating = 4.6f,
                totalRatings = 345
            ),
            Product(
                name = "Converse All Stars",
                description = "Classic canvas shoes",
                price = 3999.0,
                brand = "Converse",
                type = ShoeType.CASUAL,
                color = "Black",
                rating = 4.2f,
                totalRatings = 890
            ),
            Product(
                name = "Vans Old Skool",
                description = "Skate shoe with timeless appeal",
                price = 4499.0,
                brand = "Vans",
                type = ShoeType.CASUAL,
                color = "White/Black Checkerboard",
                rating = 4.4f,
                totalRatings = 456
            ),
            Product(
                name = "Clarks Desert Boot",
                description = "Formal yet comfortable",
                price = 7499.0,
                brand = "Clarks",
                type = ShoeType.FORMAL,
                color = "Beige",
                rating = 4.5f,
                totalRatings = 123
            ),
            Product(
                name = "Hush Puppies Loafers",
                description = "Perfect for office wear",
                price = 6999.0,
                brand = "Hush Puppies",
                type = ShoeType.FORMAL,
                color = "Brown",
                rating = 4.3f,
                totalRatings = 89
            ),
            Product(
                name = "Nike React Infinity",
                description = "Running shoes for marathons",
                price = 10999.0,
                brand = "Nike",
                type = ShoeType.SPORTS,
                color = "Blue/White",
                rating = 4.8f,
                totalRatings = 267
            ),
            Product(
                name = "Adidas Stan Smith",
                description = "Minimalist design classic",
                price = 5999.0,
                brand = "Adidas",
                type = ShoeType.CASUAL,
                color = "White/Green",
                rating = 4.4f,
                totalRatings = 678
            ),
            Product(
                name = "Puma Court Flex",
                description = "Basketball inspired casual",
                price = 5499.0,
                brand = "Puma",
                type = ShoeType.SPORTS,
                color = "White",
                rating = 4.2f,
                totalRatings = 145
            ),
            Product(
                name = "Skechers Memory Foam",
                description = "Memory foam insole support",
                price = 5499.0,
                brand = "Skechers",
                type = ShoeType.CASUAL,
                color = "Black/Gray",
                rating = 4.3f,
                totalRatings = 334
            ),
            Product(
                name = "Steve Madden Heels",
                description = "Stylish womens heels",
                price = 4999.0,
                brand = "Steve Madden",
                type = ShoeType.HEELS,
                color = "Black",
                rating = 4.1f,
                totalRatings = 189
            ),
            Product(
                name = "Birkenstock Sandals",
                description = "Comfortable orthopedic sandals",
                price = 6999.0,
                brand = "Birkenstock",
                type = ShoeType.SANDALS,
                color = "Brown",
                rating = 4.6f,
                totalRatings = 456
            ),
            Product(
                name = "Salomon Trail Runner",
                description = "Off-road trail running shoes",
                price = 8999.0,
                brand = "Salomon",
                type = ShoeType.SPORTS,
                color = "Gray/Orange",
                rating = 4.7f,
                totalRatings = 267
            ),
            Product(
                name = "Timberland Work Boots",
                description = "Durable work and outdoor boots",
                price = 9999.0,
                brand = "Timberland",
                type = ShoeType.BOOTS,
                color = "Tan/Brown",
                rating = 4.5f,
                totalRatings = 312
            )
        )

        sampleProducts.forEach { product ->
            val productId = productDao.insertProduct(product)
            // Add sizes for each product
            ShoeSize.entries.forEach { size ->
                productDao.insertProductSize(
                    ProductSize(
                        productId = productId,
                        size = size,
                        quantity = (50..200).random()
                    )
                )
            }
        }
    }
}
