package com.goodluck.shoes.data.repository

import com.goodluck.shoes.data.dao.UserDao
import com.goodluck.shoes.data.models.User
import com.goodluck.shoes.data.models.UserRole
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun registerUser(user: User): Long {
        return userDao.insert(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.getUserByEmail(email)?.takeIf { it.password == password }
    }

    suspend fun getUserById(id: Long): User? {
        return userDao.getUserById(id)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    fun getUsersByRole(role: UserRole): Flow<List<User>> {
        return userDao.getUsersByRole(role)
    }

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

    fun getActiveUsers(): Flow<List<User>> {
        return userDao.getActiveUsers()
    }

    suspend fun deleteUser(id: Long) {
        userDao.deleteUserById(id)
    }
}
