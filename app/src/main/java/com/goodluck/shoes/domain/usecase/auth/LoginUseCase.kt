package com.goodluck.shoes.domain.usecase.auth

import com.goodluck.shoes.data.models.User
import com.goodluck.shoes.data.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> = try {
        require(email.isNotBlank()) { "Email cannot be empty" }
        require(password.isNotBlank()) { "Password cannot be empty" }

        val user = userRepository.loginUser(email, password)
            ?: throw IllegalArgumentException("Invalid email or password")

        if (!user.isActive) {
            throw IllegalStateException("User account is inactive")
        }

        Result.success(user)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
