package com.example.notes.ui.home

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.adapter.OnRecipeListener
import com.example.notes.adapter.RecipeRecyclerAdapter
import com.example.notes.extensions.VerticalSpacingItemDecorator
import com.example.notes.models.Recipe
import com.example.notes.viewmodels.RecipeListViewModel
import kotlinx.android.synthetic.main.activity_recipes_list.*

class RecipeListFragment : Fragment(), OnRecipeListener {

    private var searchView: SearchView? = null
    private var viewModel: RecipeListViewModel = RecipeListViewModel()
    private lateinit var adapter: RecipeRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        subscribeObservers()
        initSearchView()
        if (!viewModel.isViewingRecipes) {
            displaySearchCategories()
        }
        this.view?.isFocusableInTouchMode = true
        this.view?.requestFocus()
        this.view?.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.d("BACK", "BACK")
                    //TODO Essa logica n deveria estar no viewModel?
                    return if (viewModel.onBackPressed()) {
                        false
                    } else {
                        displaySearchCategories()
                        true
                    }
                }
                return false
            }
        })
    }

    override fun onRecipeClick(posistion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCategoryClick(category: String) {
        adapter.displayLoading()
        viewModel.searchRecipesApi(category, 1)
        searchView?.clearFocus()
    }

    private fun subscribeObservers() {
        viewModel.recipes.observe(viewLifecycleOwner,
            Observer<List<Recipe>> { recipes ->
                if(viewModel.isViewingRecipes) {
                    adapter.recipes = recipes
                }
            })
    }

    private fun initRecyclerView() {
        adapter = RecipeRecyclerAdapter(this)
        val verticalSpacingItemDecorator = VerticalSpacingItemDecorator(30)
        recipe_list.addItemDecoration(verticalSpacingItemDecorator)
        recipe_list.adapter = adapter
        recipe_list.layoutManager = LinearLayoutManager(context)
    }

    private fun searchRecipesApi(query: String, pageNumber: Int) {
        viewModel.searchRecipesApi(query, pageNumber)
    }

    private fun initSearchView() {
        searchView = activity?.findViewById(R.id.search_view)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchRecipesApi(it, 1) }
                adapter.displayLoading()
                searchView?.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun displaySearchCategories() {
        viewModel.isViewingRecipes = false
        adapter.displaySearchCategories()
    }
}