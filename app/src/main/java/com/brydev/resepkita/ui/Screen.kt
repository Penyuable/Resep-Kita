package com.brydev.resepkita.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object UserRecipes : Screen("user_recipes", "My Recipes", Icons.Default.Restaurant)
    object Recent : Screen("recent", "Recent", Icons.Default.History)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object ChatAI : Screen("chat_ai", "AI Chat", Icons.Default.Chat)
    object CreateRecipe : Screen("create_recipe", "Create Recipe", Icons.Default.Add)
    object Detail : Screen("detail/{recipeId}", "Detail") {
        fun createRoute(recipeId: String) = "detail/$recipeId"
    }
}
