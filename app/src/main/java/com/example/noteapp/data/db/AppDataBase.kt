package com.example.noteapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.data.db.daos.BackgroundColorDAO
import com.example.noteapp.data.db.daos.NoteDao
import com.example.noteapp.data.models.BackgroundColor
import com.example.noteapp.data.models.NoteModel

@Database(entities = [NoteModel::class, BackgroundColor::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun backgroundColorDAO(): BackgroundColorDAO
}

