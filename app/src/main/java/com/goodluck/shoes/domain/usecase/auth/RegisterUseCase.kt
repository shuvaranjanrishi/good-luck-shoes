package com.goodluck.shoes.domain.usecase.auth

import com.goodluck.shoes.data.models.User
import com.goodluck.shoes.data.models.UserRole
import com.goodluck.shoes.data.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        phone: String,
        password: String,
        role: UserRole,
        address: String = ""
    ): Result<Long> = try {
        // Validation
        require(name.isNotBlank()) { "Name cannot be empty" }
        require(email.isNotBlank()) { "Email cannot be empty" }
        require(phone.isNotBlank()) { "Phone cannot be empty" }
        require(password.length >= 6) { "Password must be at least 6 characters" }

        val user = User(
            name = name,
            email = email,
            phone = phone,
            password = password, // TODO: Hash password properly in production
            role = role,
            address = address
        )

        val userId = userRepository.registerUser(user)
        Result.success(userId)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
