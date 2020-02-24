package com.example.notes

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.adapter.OnRecipeListener
import com.example.notes.adapter.RecipeRecyclerAdapter
import com.example.notes.models.Recipe
import com.example.notes.viewmodels.RecipeListViewModel
import kotlinx.android.synthetic.main.activity_recipes_list.*

class RecipeListActivity : BaseActivity(), OnRecipeListener {
    private var recipeListViewModel: RecipeListViewModel = RecipeListViewModel()
    private lateinit var adapter : RecipeRecyclerAdapter


    companion object {
        const val TAG: String = "RecipeListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes_list)
        initRecyclerView()
        subscribeObservers()
        searchRecipesApi("chicken", 1)
    }

    private fun subscribeObservers() {
        recipeListViewModel.recipes.observe(this,
            Observer<List<Recipe>> { recipes ->
                adapter.recipes = recipes
            })
    }

    private fun initRecyclerView() {
        adapter = RecipeRecyclerAdapter(this)
        recipe_list.adapter = adapter
        recipe_list.layoutManager = LinearLayoutManager(this)
    }

    private fun searchRecipesApi(query: String, pageNumber: Int) {
        recipeListViewModel.searchRecipesApi(query, pageNumber)
    }

    override fun onRecipeClick(posistion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCategoryClick(category: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
