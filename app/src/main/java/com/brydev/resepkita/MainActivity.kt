package com.brydev.resepkita

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.brydev.resepkita.ui.RecipeViewModel
import com.brydev.resepkita.ui.Screen
import com.brydev.resepkita.ui.screens.*
import com.brydev.resepkita.ui.theme.ResepKitaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResepKitaTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val viewModel: RecipeViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        Screen.Home,
        Screen.Recent,
        Screen.ChatAI,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {
            // Sembunyikan bottom bar di halaman Detail dan Create Recipe
            val isMainScreen = currentDestination?.route in items.map { it.route }
            if (isMainScreen) {
                NavigationBar {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { screen.icon?.let { Icon(it, contentDescription = screen.title) } },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onCreateClick = { navController.navigate(Screen.CreateRecipe.route) },
                    onRecipeClick = { recipeId ->
                        navController.navigate(Screen.Detail.createRoute(recipeId))
                    }
                )
            }
            composable(Screen.Recent.route) {
                RecentScreen(viewModel) { recipeId ->
                    navController.navigate(Screen.Detail.createRoute(recipeId))
                }
            }
            composable(Screen.ChatAI.route) {
                ChatAIScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.CreateRecipe.route) {
                CreateRecipeScreen(viewModel) {
                    navController.popBackStack()
                }
            }
            composable(Screen.Detail.route) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getString("recipeId")
                recipeId?.let {
                    DetailScreen(it, viewModel) {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}
