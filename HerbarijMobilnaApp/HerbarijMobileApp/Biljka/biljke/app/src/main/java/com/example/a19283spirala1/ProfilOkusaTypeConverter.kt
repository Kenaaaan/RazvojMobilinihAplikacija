package com.example.a19283spirala1

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProfilOkusaTypeConverter {
    @TypeConverter
    fun fromProfilOkusa(profilOkusa: ProfilOkusaBiljke?): String? {
        val gson = Gson()
        return gson.toJson(profilOkusa)
    }

    @TypeConverter
    fun toProfilOkusa(data: String?): ProfilOkusaBiljke? {
        val gson = Gson()
        val type = object : TypeToken<ProfilOkusaBiljke?>() {}.type
        return gson.fromJson(data, type)
    }
}
