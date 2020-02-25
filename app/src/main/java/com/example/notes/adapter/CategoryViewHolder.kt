package com.example.notes.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import de.hdodenhof.circleimageview.CircleImageView

class CategoryViewHolder(itemView: View, var listener: OnRecipeListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var categoryImage: CircleImageView = itemView.findViewById(R.id.category_image)
    var categoryTitle: TextView = itemView.findViewById(R.id.category_title)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        listener.onCategoryClick(categoryTitle.text.toString())
    }
}