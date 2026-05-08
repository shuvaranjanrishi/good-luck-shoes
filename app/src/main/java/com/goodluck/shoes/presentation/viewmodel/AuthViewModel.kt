package com.goodluck.shoes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodluck.shoes.data.models.User
import com.goodluck.shoes.data.models.UserRole
import com.goodluck.shoes.domain.usecase.auth.LoginUseCase
import com.goodluck.shoes.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val user: User? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            loginUseCase(email, password).onSuccess { user ->
                _authState.value = AuthState(
                    isLoading = false,
                    isSuccess = true,
                    user = user
                )
            }.onFailure { error ->
                _authState.value = AuthState(
                    isLoading = false,
                    error = error.message ?: "Login failed"
                )
            }
        }
    }

    fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        role: UserRole
    ) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            registerUseCase(name, email, phone, password, role).onSuccess {
                _authState.value = AuthState(
                    isLoading = false,
                    isSuccess = true
                )
            }.onFailure { error ->
                _authState.value = AuthState(
                    isLoading = false,
                    error = error.message ?: "Registration failed"
                )
            }
        }
    }

    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }

    fun resetState() {
        _authState.value = AuthState()
    }
}
