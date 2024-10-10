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

    var isPlay: Boolean
        get() = sharedPreferences.getBoolean(TEXT_BOOLEAN, true)
        set(value) = sharedPreferences.edit().putBoolean(TEXT_BOOLEAN, value).apply()

    companion object {
        const val SHARED_KEY = "shared_keyy"
        const val TEXT_KEY = "text"
        const val TEXT_BOOLEAN = "bool"
    }
}