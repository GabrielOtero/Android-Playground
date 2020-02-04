package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.adapter.NotesRecyclerAdapter
import com.example.notes.adapter.OnNoteListener
import com.example.notes.extensions.VerticalSpacingItemDecorator
import com.example.notes.models.Note
import com.example.notes.repository.NoteRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NoteListActivity : AppCompatActivity(), OnNoteListener, View.OnClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noteRepository: NoteRepository

    private var notes = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        recyclerView = findViewById(R.id.recycler_view)

        noteRepository = NoteRepository(this)
        retrieveNotes()
//        insertFakeNotes()
        initRecyclerView()

        val toolbar = findViewById<Toolbar>(R.id.notes_toolbar)
        setSupportActionBar(toolbar)
        title = "Notes"

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener(this)

    }

    private fun retrieveNotes() {
        noteRepository.retrieveNotesTask().observe(this, Observer { notesDb ->
            if (notesDb.isNotEmpty()) {
                notes.clear()
                notes.addAll(notesDb)
            }
            recyclerView.adapter?.notifyDataSetChanged()
        })

    }

    fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NotesRecyclerAdapter(notes, this)
        recyclerView.addItemDecoration(VerticalSpacingItemDecorator(10))
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)
    }

    override fun onNoteClick(position: Int) {
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra("selected_note", notes[position])
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, NoteActivity::class.java)
        startActivity(intent)
    }

    private fun deleteNote(note: Note) {
        noteRepository.deleteNote(note)
        notes.remove(note)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            deleteNote(notes[viewHolder.adapterPosition])
        }
    }
}
