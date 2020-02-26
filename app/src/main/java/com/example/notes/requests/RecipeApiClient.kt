package com.example.notes.requests

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.notes.AppExecutors
import com.example.notes.models.Recipe
import com.example.notes.responses.RecipeSearchResponse
import com.example.notes.util.Constants
import com.example.notes.util.Constants.Companion.NETWORK_TIMEOUT
import retrofit2.Call
import java.io.IOException
import java.util.concurrent.TimeUnit


object RecipeApiClient {
    var recipes: MutableLiveData<MutableList<Recipe>> = MutableLiveData()
    const val TAG: String = "RecipeApiClient"
    private var retrieveRecipesRunnable: RetrieveRecipesRunnable? = null

    fun searchRecipesApi(query: String, pageNumber: Int) {
        if (retrieveRecipesRunnable != null) {
            retrieveRecipesRunnable = null
        }
        retrieveRecipesRunnable = RetrieveRecipesRunnable(query, pageNumber)

        var handler = AppExecutors.networkIO.submit(retrieveRecipesRunnable)

        AppExecutors.networkIO.schedule({
            // Let the user know its timed out
            handler.cancel(true)
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    fun cancelRequest() {
        retrieveRecipesRunnable?.cancelRequest
    }


    private class RetrieveRecipesRunnable(
        var query: String,
        var pageNumber: Int,
        var cancelRequest: Boolean = false
    ) : Runnable {
        override fun run() {
            if (cancelRequest) return
            try {
                val response = getRecipes(query, pageNumber).execute()
                if (response.code() == 200) {
                    val list = response.body()?.recipes as MutableList<Recipe>
                    if (pageNumber == 1) {
                        recipes.postValue(list)
                    } else {
                        val currentRecipes: MutableList<Recipe>? = recipes.value
                        currentRecipes?.addAll(list)
                    }
                } else {

                    Log.e(TAG, response.errorBody().toString())
                    recipes.postValue(null)
                }
            } catch (e: IOException) {

            }
        }

        private fun getRecipes(query: String, pageNumber: Int): Call<RecipeSearchResponse> {
            return ServiceGenerator.recipeApi.searchRecipe(
                Constants.API_KEY,
                query,
                pageNumber.toString()
            )
        }

        private fun cancelRequest() {
            Log.e(TAG, "Request Canceled")
            cancelRequest = true
        }


    }
}