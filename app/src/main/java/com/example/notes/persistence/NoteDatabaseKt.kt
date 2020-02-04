package com.example.notes.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.models.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {

        private const val DATABASE_NAME = "notes_db"
        private var instance: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return instance as NoteDatabase
        }
    }
}