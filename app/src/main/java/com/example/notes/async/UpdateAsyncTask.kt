package com.example.notes.async

import android.os.AsyncTask
import com.example.notes.models.Note
import com.example.notes.persistence.NoteDao

//TODO Fazer com Coroutines
class UpdateAsyncTask(var noteDao : NoteDao) : AsyncTask<Note, Void, Void>() {

    override fun doInBackground(vararg notes: Note?): Void? {
        noteDao.updateNote(*notes)
        return null
    }


}