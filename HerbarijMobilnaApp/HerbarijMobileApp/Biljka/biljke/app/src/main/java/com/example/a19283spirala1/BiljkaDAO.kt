package com.example.a19283spirala1

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

@Dao
interface BiljkaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBiljka(biljka: Biljka): Long

    @Transaction
    suspend fun saveBiljka(biljka: Biljka):Boolean {
        var succ = false
        withContext(Dispatchers.IO) {
            val insertedId = insertBiljka(biljka)
            succ = insertedId != -1L
        }
        return succ
    }

    @Query("SELECT * FROM Biljka WHERE onlineChecked = 0")
    suspend fun getOfflineBiljka(): List<Biljka>


    @Update
    suspend fun updateOfflineBiljka(biljke: List<Biljka>)

    @Transaction
    suspend fun fixOfflineBiljka(): Int {
        val changed = AtomicInteger(0)
        withContext(Dispatchers.IO) {
            val biljke = getOfflineBiljka()
            val trefleDao = TrefleDAO()
            val noveBiljke = biljke.map { biljka ->
                val novaBiljka = trefleDao.fixData(biljka)
                if (novaBiljka != biljka) changed.incrementAndGet()
                novaBiljka
            }
            updateOfflineBiljka(noveBiljke)
        }
        return changed.get()
    }

    @Insert
    suspend fun addBiljkaBitmap(biljkaBitmap: BiljkaBitmap)


    @Query("SELECT COUNT(*) FROM biljka WHERE id = :id")
    suspend fun checkBiljka(id: Long): Int

    @Query("SELECT COUNT(*) FROM biljkaBitmap WHERE idBiljke = :biljkaId")
    suspend fun biljkaBitmapExists(biljkaId: Long): Int

    @Query("SELECT * FROM BiljkaBitmap WHERE idBiljke = :biljkaId")
    suspend fun getBiljkaBitmapById(biljkaId: Long): BiljkaBitmap?

    @Transaction
    suspend fun addImage(idBiljke: Long,bitmap: Bitmap):Boolean {
        var succ = true
        val slika = BiljkaBitmap(idBiljke, bitmap)
        withContext(Dispatchers.IO) {
            if(checkBiljka(idBiljke) == 0) {
                println("Ne postoji biljka u bazi")
                succ = false
            }
            else if(biljkaBitmapExists(idBiljke) > 0){
                println("Već postoji slika u bazi")
                succ = false
            }
            else {
                addBiljkaBitmap(slika)
            }
        }
        return succ
    }

    @Query("SELECT * FROM Biljka")
    suspend fun getAllBiljkas(): List<Biljka>

    @Query("DELETE FROM Biljka")
    suspend fun clearData()

}