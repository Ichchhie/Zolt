package com.example.woltapplication.utils

import android.content.Context

class StringProvider(private val context: Context) {
    fun getString(resId: Int): String = context.getString(resId)
}
