package com.example.notes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.adapter.OnRecipeListener
import com.example.notes.adapter.RecipeRecyclerAdapter
import com.example.notes.models.Recipe
import com.example.notes.viewmodels.RecipeListViewModel
import kotlinx.android.synthetic.main.activity_recipes_list.*

class RecipeListFragment : Fragment(), OnRecipeListener {

    private lateinit var recipeListViewModel: RecipeListViewModel
    private lateinit var adapter: RecipeRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recipeListViewModel =
            ViewModelProviders.of(this).get(RecipeListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_recipes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        subscribeObservers()
        searchRecipesApi("chicken", 1)
    }

    private fun subscribeObservers() {
        recipeListViewModel.recipes.observe(viewLifecycleOwner,
            Observer<List<Recipe>> { recipes ->
                adapter.recipes = recipes
            })
    }

    private fun initRecyclerView() {
        adapter = RecipeRecyclerAdapter(this)
        recipe_list.adapter = adapter
        recipe_list.layoutManager = LinearLayoutManager(context)
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