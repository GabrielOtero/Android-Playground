package com.example.notes.requests

import com.example.notes.responses.RecipeResponse
import com.example.notes.responses.RecipeSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("api/search")
    fun searchRecipe(@Query("key") key: String, @Query("q") q: String, @Query("page") page: String): Call<RecipeSearchResponse>

    @GET("api/get")
    fun getRecipe(@Query("key") key: String, @Query("rId") rId: String): Call<RecipeResponse>
}