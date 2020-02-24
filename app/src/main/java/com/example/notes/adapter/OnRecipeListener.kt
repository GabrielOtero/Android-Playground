package com.example.notes.adapter

interface OnRecipeListener {
    fun onRecipeClick(posistion: Int)
    fun onCategoryClick(category: String)
}