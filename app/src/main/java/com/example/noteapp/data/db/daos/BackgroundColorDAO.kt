package com.example.noteapp.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noteapp.data.models.BackgroundColor
@Dao

interface  BackgroundColorDAO {
    @Query("SELECT*FROM bgColor LIMIT 1  ")
     fun getBackgroundColor():LiveData<BackgroundColor?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBackgroundColor(color: BackgroundColor)
}