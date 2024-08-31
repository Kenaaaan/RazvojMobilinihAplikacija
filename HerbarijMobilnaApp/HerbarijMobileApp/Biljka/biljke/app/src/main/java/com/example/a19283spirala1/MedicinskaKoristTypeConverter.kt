package com.example.a19283spirala1

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MedicinskaKoristTypeConverter {
    @TypeConverter
    fun fromMedicinskeKoristiList(medicinskeKoristi: List<MedicinskaKorist>): String {
        val gson = Gson()
        return gson.toJson(medicinskeKoristi)
    }

    @TypeConverter
    fun toMedicinskeKoristiList(data: String): List<MedicinskaKorist> {
        val gson = Gson()
        val listType = object : TypeToken<List<MedicinskaKorist>>() {}.type
        return gson.fromJson(data, listType)
    }
}
