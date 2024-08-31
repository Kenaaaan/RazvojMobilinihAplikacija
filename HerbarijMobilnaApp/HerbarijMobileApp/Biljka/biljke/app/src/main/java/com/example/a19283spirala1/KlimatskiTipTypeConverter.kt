package com.example.a19283spirala1

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class KlimatskiTipTypeConverter {
    @TypeConverter
    fun fromKlimatskiTipList(klimatskiTipovi: List<KlimatskiTip>): String {
        val gson = Gson()
        return gson.toJson(klimatskiTipovi)
    }

    @TypeConverter
    fun toKlimatskiTipList(data: String): List<KlimatskiTip> {
        val gson = Gson()
        val listType = object : TypeToken<List<KlimatskiTip>>() {}.type
        return gson.fromJson(data, listType)
    }
}
