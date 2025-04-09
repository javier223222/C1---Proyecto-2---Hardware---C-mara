package com.example.myapplication.data.repository


import android.content.Context

object PreferencesManager {

    fun saveLastUsage(context: Context) {
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val currentTime = System.currentTimeMillis()
        editor.putLong("lastUsage", currentTime)
        editor.apply()
    }

    fun getLastUsage(context: Context): Long {
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getLong("lastUsage", 0)
    }
}