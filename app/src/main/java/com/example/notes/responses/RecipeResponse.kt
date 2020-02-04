package com.example.notes.responses

import com.example.notes.models.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RecipeResponse(@SerializedName("recipe") @Expose var recipe: Recipe)