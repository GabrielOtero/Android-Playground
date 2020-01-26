package com.example.notes.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.notes.async.DeleteAsyncTask
import com.example.notes.async.InsertAsyncTask
import com.example.notes.async.UpdateAsyncTask
import com.example.notes.models.Note
import com.example.notes.persistence.NoteDatabase

class NoteRepository(context: Context) {

    private var noteDatabase: NoteDatabase = NoteDatabase.getInstance(context)

    fun insertNoteTask(note: Note) {
        InsertAsyncTask(noteDatabase.noteDao).execute(note)
    }

    fun updateNote(note: Note) {
        UpdateAsyncTask(noteDatabase.noteDao).execute(note)

    }

    fun retrieveNotesTask(): LiveData<List<Note>> {
        return noteDatabase.noteDao.getNotes()
    }

    fun deleteNote(note: Note) {
        DeleteAsyncTask(noteDatabase.noteDao).execute(note)
    }


}