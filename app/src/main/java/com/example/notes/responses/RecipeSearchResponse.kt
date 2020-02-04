package com.example.notes.responses

import com.example.notes.models.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RecipeSearchResponse(
    @SerializedName("count")
    @Expose
    var count: Int,
    @SerializedName("recipes")
    @Expose
    var recipes: List<Recipe>
)