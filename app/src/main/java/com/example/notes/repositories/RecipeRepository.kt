package com.example.notes.repositories

import androidx.lifecycle.MutableLiveData
import com.example.notes.models.Recipe
import com.example.notes.requests.RecipeApiClient

object RecipeRepository {
    var recipes: MutableLiveData<MutableList<Recipe>> = RecipeApiClient.recipes

    fun searchRecipesApi(query: String, pageNumber : Int){
        RecipeApiClient.searchRecipesApi(query, pageNumber)
    }
}