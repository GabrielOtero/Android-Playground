package com.example.notes.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.models.Recipe
import com.example.notes.repositories.RecipeRepository

class RecipeListViewModel : ViewModel() {
    var recipes: MutableLiveData<MutableList<Recipe>> = RecipeRepository.recipes
    var isViewingRecipes: Boolean = false
    var isPerformingQuery: Boolean = false

    fun searchRecipesApi(query: String, pageNumber: Int) {
        isViewingRecipes = true
        isPerformingQuery = true
        RecipeRepository.searchRecipesApi(query, pageNumber)
    }

    fun onBackPressed(): Boolean {
        if (isPerformingQuery) {
            RecipeRepository.cancelRequest()
            isPerformingQuery = false
        } else if (isViewingRecipes) {
            isViewingRecipes = false
            return false
        }

        return true

    }
}