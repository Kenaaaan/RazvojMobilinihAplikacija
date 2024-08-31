package com.example.a19283spirala1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Biljka::class, BiljkaBitmap::class], version = 1)
@TypeConverters(
    MedicinskaKoristTypeConverter::class,
    ProfilOkusaTypeConverter::class,
    StringListTypeConverter::class,
    KlimatskiTipTypeConverter::class,
    ZemljisteTypeConverter::class,
    BitmapConverter::class
)
abstract class BiljkaDatabase : RoomDatabase() {
    abstract fun biljkaDao(): BiljkaDAO

    companion object {
        @Volatile
        private var INSTANCE: BiljkaDatabase? = null

        fun getInstance(context: Context): BiljkaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = buildRoomDB(context)
                INSTANCE = instance
                instance
            }
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BiljkaDatabase::class.java,
                "biljke-db"
            ).build()
    }

}