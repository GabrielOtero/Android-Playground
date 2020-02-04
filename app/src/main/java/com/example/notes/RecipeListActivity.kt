package com.example.notes

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.notes.requests.ServiceGenerator
import com.example.notes.responses.RecipeSearchResponse
import com.example.notes.util.Constants
import kotlinx.android.synthetic.main.activity_recipes_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeListActivity : BaseActivity() , View.OnClickListener {
    override fun onClick(v: View?) {
        testRetrofitRequest()
    }

    companion object {
        const val TAG: String = "RecipeListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes_list)
        test.setOnClickListener(this)
    }

    fun testRetrofitRequest() {
        val recipeApi = ServiceGenerator.recipeApi

        val responseCall = recipeApi.searchRecipe(Constants.API_KEY, "chicken", "1")

        responseCall.enqueue(object : Callback<RecipeSearchResponse> {
            override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                Log.d(TAG, t.message)
            }

            override fun onResponse(
                call: Call<RecipeSearchResponse>,
                response: Response<RecipeSearchResponse>
            ) {
                Log.d(TAG, response.toString())
                if (response.code() == 200) {
                    Log.d(TAG, response.body().toString())
                    val recipes = response.body()?.recipes
                    recipes?.forEach {
                        Log.d(TAG, it.title)
                    }
                } else {
                    Log.d(TAG, response.errorBody().toString())
                }

            }
        })
    }
}
