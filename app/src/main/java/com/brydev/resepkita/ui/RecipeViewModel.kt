package com.brydev.resepkita.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brydev.resepkita.data.Recipe
import com.brydev.resepkita.data.RecipeApiService
import com.brydev.resepkita.data.RecipeCreateRequest
import com.brydev.resepkita.data.UserRecipeApiService
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val apiService = RecipeApiService.create()
    private val userApiService = UserRecipeApiService.create()

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    private val _userRecipes = MutableStateFlow<List<Recipe>>(emptyList())

    // Properti publik agar bisa diakses oleh Screen
    val userRecipes: StateFlow<List<Recipe>> = _userRecipes.asStateFlow()

    // Menggabungkan resep umum dan resep buatan sendiri
    val allRecipes: StateFlow<List<Recipe>> = combine(_recipes, _userRecipes) { common, user ->
        user + common // Resep lokal muncul di atas
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _recentRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recentRecipes: StateFlow<List<Recipe>> = _recentRecipes

    private val _searchQuery = MutableStateFlow("")

    init {
        fetchCategories()
        fetchRecipes()
        fetchUserRecipes()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = apiService.getCategories()
                val categoryList = response.meals?.map { it.strCategory } ?: emptyList()
                _categories.value = listOf("All") + categoryList
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching categories", e)
            }
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        if (category == "All") {
            fetchRecipes(_searchQuery.value)
        } else {
            fetchRemoteByCategory(category)
        }
    }

    private fun fetchRemoteByCategory(category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.filterByCategory(category)
                _recipes.value = response.meals?.map { it.toRecipe() } ?: emptyList()
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching category remote", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchRecipes(query: String? = null) {
        val currentQuery = query ?: ""
        _searchQuery.value = currentQuery
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = apiService.searchRecipes(currentQuery)
                val mealList = response.meals
                if (mealList != null) {
                    _recipes.value = mealList.map { it.toRecipe() }
                } else {
                    _recipes.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching recipes", e)
                _errorMessage.value = "Terjadi kesalahan koneksi."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchUserRecipes() {
        viewModelScope.launch {
            try {
                val recipes = userApiService.getUserRecipes()
                _userRecipes.value = recipes
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching user recipes", e)
            }
        }
    }

    fun createRecipe(request: RecipeCreateRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                userApiService.createRecipe(request)
                fetchUserRecipes()
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Gagal membuat resep: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getRecipeById(id: String): Recipe? {
        val recipe = allRecipes.value.find { it.id == id } 
            ?: _recentRecipes.value.find { it.id == id }
        if (recipe != null) {
            addToRecent(recipe)
        }
        return recipe
    }

    private fun addToRecent(recipe: Recipe) {
        val currentList = _recentRecipes.value.toMutableList()
        if (currentList.none { it.id == recipe.id }) {
            currentList.add(0, recipe)
            _recentRecipes.value = currentList.take(10)
        }
    }
}
