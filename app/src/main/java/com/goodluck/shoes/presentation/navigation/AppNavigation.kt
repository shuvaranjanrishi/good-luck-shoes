package com.goodluck.shoes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.goodluck.shoes.data.models.UserRole
import com.goodluck.shoes.presentation.screens.auth.LoginScreen
import com.goodluck.shoes.presentation.screens.auth.RegisterScreen
import com.goodluck.shoes.presentation.screens.auth.RoleSelectionScreen
import com.goodluck.shoes.presentation.screens.customer.ProductListingScreen

object Routes {
    const val ROLE_SELECTION = "role_selection"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val PRODUCT_LISTING = "product_listing"
    const val PRODUCT_DETAILS = "product_details/{productId}"
    const val ORDER_HISTORY = "order_history"
    const val SELLER_DASHBOARD = "seller_dashboard"
    const val OWNER_DASHBOARD = "owner_dashboard"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.ROLE_SELECTION
    ) {
        composable(Routes.ROLE_SELECTION) {
            RoleSelectionScreen(
                onRoleSelected = { role ->
                    // Navigate to login with role info
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.ROLE_SELECTION) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.PRODUCT_LISTING) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.PRODUCT_LISTING) {
            ProductListingScreen(
                onProductClick = { product ->
                    // Navigate to product details
                    navController.navigate(Routes.PRODUCT_DETAILS.replace("{productId}", product.id.toString()))
                }
            )
        }
    }
}
