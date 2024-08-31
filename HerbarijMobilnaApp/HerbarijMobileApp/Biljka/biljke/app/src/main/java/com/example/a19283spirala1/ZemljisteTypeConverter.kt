package com.example.a19283spirala1

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ZemljisteTypeConverter {
    @TypeConverter
    fun fromZemljisteList(zemljisniTipovi: List<Zemljiste>): String {
        val gson = Gson()
        return gson.toJson(zemljisniTipovi)
    }

    @TypeConverter
    fun toZemljisteList(data: String): List<Zemljiste> {
        val gson = Gson()
        val listType = object : TypeToken<List<Zemljiste>>() {}.type
        return gson.fromJson(data, listType)
    }
}
