package com.example.notes.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.notes.R
import com.example.notes.models.Recipe
import com.example.notes.util.Constants
import kotlin.math.roundToInt

class RecipeRecyclerAdapter(var onRecipeListener: OnRecipeListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val RECIPE_TYPE = 1
        const val LOADING_TYPE = 2
        const val CATEGORY_TYPE = 3
    }


    var recipes: List<Recipe> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            RECIPE_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_recipe_list_item, parent, false)
                return RecipeViewHolder(view, onRecipeListener)

            }
            LOADING_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_loading_list_item, parent, false)
                return LoadingViewHolder(view)
            }
            CATEGORY_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_category_list_item, parent, false)
                return CategoryViewHolder(view, onRecipeListener)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_loading_list_item, parent, false)
                return RecipeViewHolder(view, onRecipeListener)
            }
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (recipes[position].socialRank == -1F) {
            CATEGORY_TYPE
        } else if (recipes[position].title.equals("LOADING...")) {
            LOADING_TYPE
        } else {
            RECIPE_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var itemViewType = getItemViewType(position)
        if (itemViewType == RECIPE_TYPE) {
            val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)

            Glide.with(holder.itemView.context).setDefaultRequestOptions(requestOptions)
                .load(recipes[position].imageUrl).into((holder as RecipeViewHolder).image)

            holder.title.text = recipes[position].title
            holder.publisher.text = recipes[position].publisher
            holder.socialScore.text =
                recipes[position].socialRank?.let { it.roundToInt().toString() }
        } else if (itemViewType == CATEGORY_TYPE) {
            val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)
            var path =
                Uri.parse("android.resource://com.example.notes/drawable/" + recipes[position].imageUrl)
            Glide.with(holder.itemView.context).setDefaultRequestOptions(requestOptions)
                .load(path).into((holder as CategoryViewHolder).categoryImage)
            holder.categoryTitle.text = recipes[position].title

        }
    }

    fun displayLoading() {
        if (!isLoading()) {
            var recipe = Recipe()
            recipe.title = "LOADING..."
            var loadingList = listOf(recipe)
            recipes = loadingList
            notifyDataSetChanged()
        }
    }

    fun displaySearchCategories() {
        val categories = mutableListOf<Recipe>()
        for (i in Constants.DEFAULT_SEARCH_CATEGORIES.indices) {
            var recipe = Recipe()
            recipe.title = Constants.DEFAULT_SEARCH_CATEGORIES[i]
            recipe.imageUrl = Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]
            recipe.socialRank = -1F
            categories.add(recipe)
        }

        recipes = categories
        notifyDataSetChanged()
    }


    private fun isLoading(): Boolean {
        if (recipes.size > 0) {
            if (recipes.last().title.equals("LOADING...")) {
                return true
            }
        }
        return false
    }
}