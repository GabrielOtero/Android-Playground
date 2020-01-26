package com.example.notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.models.Note

class NotesRecyclerAdapter(val notes: ArrayList<Note>, private val onNoteListener: OnNoteListener) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_note_list_item, parent, false)
        return ViewHolder(view, onNoteListener)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = notes[position].title
        holder.timeStamp.text = notes[position].timeStamp
    }

}

class ViewHolder(private val view: View, private val onNoteListener: OnNoteListener) :
    RecyclerView.ViewHolder(view), View.OnClickListener {

    val timeStamp: TextView by lazy { view.findViewById<TextView>(R.id.note_timestamp) }
    val title: TextView by lazy { view.findViewById<TextView>(R.id.note_title) }

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onNoteListener.onNoteClick(adapterPosition)
    }
}

interface OnNoteListener {
    fun onNoteClick(position: Int)
}
