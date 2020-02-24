package com.example.notes.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.models.Recipe
import com.example.notes.repositories.RecipeRepository

class RecipeListViewModel : ViewModel() {
    var recipes: MutableLiveData<MutableList<Recipe>> = RecipeRepository.recipes

    fun searchRecipesApi(query: String, pageNumber: Int) {
        RecipeRepository.searchRecipesApi(query, pageNumber)
    }
}