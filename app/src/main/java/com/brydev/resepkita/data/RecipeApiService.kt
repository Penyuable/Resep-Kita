package com.brydev.resepkita.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("search.php")
    suspend fun searchRecipes(
        @Query("s") query: String = ""
    ): RecipeResponse

    @GET("filter.php")
    suspend fun filterByCategory(
        @Query("c") category: String
    ): RecipeResponse

    @GET("list.php?c=list")
    suspend fun getCategories(): CategoryResponse

    @GET("lookup.php")
    suspend fun lookupRecipe(
        @Query("i") id: String
    ): RecipeResponse

    companion object {
        private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

        fun create(): RecipeApiService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipeApiService::class.java)
        }
    }
}

data class CategoryResponse(
    val meals: List<CategoryItem>?
)

data class CategoryItem(
    val strCategory: String
)
