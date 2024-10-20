package com.example.noteapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bgColor")
data class BackgroundColor(
    @PrimaryKey val id: Int = 1,
    val color: Int
)
