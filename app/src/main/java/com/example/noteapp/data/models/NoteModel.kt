package com.example.noteapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteModel")
data class NoteModel(
    val text: String,
    val description: String,
    val color:Int
){
    @PrimaryKey(autoGenerate = true)
    var id:Int  = 0
}

