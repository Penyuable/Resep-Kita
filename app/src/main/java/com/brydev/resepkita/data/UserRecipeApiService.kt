package com.brydev.resepkita.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserRecipeApiService {
    @GET("recipes")
    suspend fun getUserRecipes(): List<Recipe>

    @POST("recipes")
    suspend fun createRecipe(@Body recipe: RecipeCreateRequest): Recipe

    companion object {
        // Gunakan 10.0.2.2 jika menggunakan Emulator Android untuk akses localhost komputer
        private const val BASE_URL = "http://10.0.2.2:8000/api/"

        fun create(): UserRecipeApiService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserRecipeApiService::class.java)
        }
    }
}

data class RecipeCreateRequest(
    val title: String,
    val category: String,
    val image: String,
    val ingredients: List<String>,
    val steps: List<String>
)
