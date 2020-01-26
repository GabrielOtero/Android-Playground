package com.example.notes.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.notes.async.InsertAsyncTask
import com.example.notes.models.Note
import com.example.notes.persistence.NoteDatabase

class NoteRepository(var context: Context) {

    private var noteDatabase: NoteDatabase

    init {
        noteDatabase = NoteDatabase.getInstance(context)
    }

    fun insertNoteTask(note: Note) {
        InsertAsyncTask(noteDatabase.noteDao).execute(note)
    }

    fun updateNote(note: Note) {

    }

    fun retrieveNotesTask(): LiveData<List<Note>> {
        return noteDatabase.noteDao.getNotes()
    }

    fun deleteNote(note: Note) {

    }


}