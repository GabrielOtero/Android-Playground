package com.example.notes.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    @SerializedName("title") var title: String? = "",
    @SerializedName("publisher") var publisher: String? = "",
    @SerializedName("ingredients") var ingredients: List<String?> = arrayListOf(),
    @SerializedName("recipe_id") var recipeId: String? = "",
    @SerializedName("image_url") var imageUrl: String? = "",
    @SerializedName("social_rank") var socialRank: Float? = 0F
) : Parcelable