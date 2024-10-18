package com.example.noteapp.ui.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {
    private lateinit var sharedPreferences: SharedPreferences

    fun unit(context: Context) {
        sharedPreferences = context.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)
    }

    var text: String?
        get() = sharedPreferences.getString(TEXT_KEY, "")
        set(value) = sharedPreferences.edit().putString(TEXT_KEY, value)!!.apply()

    fun isShow():Boolean{
     return  sharedPreferences.getBoolean(TEXT_BOOLEAN2,false)
    }
    fun onShowed(){
        sharedPreferences.edit().putBoolean(TEXT_BOOLEAN2,true).apply()
    }

    companion object {
        const val SHARED_KEY = "shared_keyy"
        const val TEXT_KEY = "text"
        const val TEXT_BOOLEAN2 = "bool2"

    }
}