package com.example.notes.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.models.Note

@Dao
interface NoteDao{
    @Insert
    fun insertNotes(vararg notes: Note?) : Array<Long>

    @Query("SELECT * FROM notes")
    fun getNotes() : LiveData<List<Note>>

    @Delete
    fun deleteNotes(vararg note : Note) : Int

    @Update
    fun updateNote(vararg note : Note) : Int
}