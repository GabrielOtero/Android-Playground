package com.example.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.notes.R
import com.example.notes.models.Recipe
import kotlin.math.roundToInt

class RecipeRecyclerAdapter(var onRecipeListener : OnRecipeListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var recipes: List<Recipe> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_recipe_list_item, parent, false)
        return RecipeViewHolder(view, onRecipeListener)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)

        Glide.with(holder.itemView.context).setDefaultRequestOptions(requestOptions)
            .load(recipes[position].imageUrl).into((holder as RecipeViewHolder).image)

        holder.title.text = recipes[position].title
        holder.publisher.text = recipes[position].publisher
        holder.socialScore.text =
            recipes[position].socialRank?.let { it.roundToInt().toString() }
    }


}