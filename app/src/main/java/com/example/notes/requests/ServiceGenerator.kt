package com.example.notes.requests

import com.example.notes.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {
    companion object {
        private val retrofitBuilder = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        private val retrofit = retrofitBuilder.build()

        var recipeApi: RecipeApi = retrofit.create(RecipeApi::class.java)
    }
}
