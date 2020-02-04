package com.example.notes.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    var title: String? = "",
    var publisher: String? = "",
    var ingredients: List<String?> = arrayListOf(),
    var recipeId: String? = "",
    var imageUrl: String? = "",
    var socialRank: Float? = 0F
) : Parcelable