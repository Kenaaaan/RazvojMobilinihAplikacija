package com.example.a19283spirala1

import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BiljkaRepository(private val biljkaDao: BiljkaDAO) {

    suspend fun saveBiljka(biljka: Biljka): Boolean {
        return withContext(Dispatchers.IO) {
            biljkaDao.saveBiljka(biljka)

        }
    }

    suspend fun fixOfflineBiljka(): Int {
        return withContext(Dispatchers.IO) {
            biljkaDao.fixOfflineBiljka()
        }
    }

    suspend fun addImage(idBiljke: Long, bitmap: Bitmap): Boolean {
        return withContext(Dispatchers.IO) {
            biljkaDao.addImage(idBiljke, bitmap)
        }
    }

    suspend fun getAllBiljkas(): List<Biljka> {
        return withContext(Dispatchers.IO) {
            biljkaDao.getAllBiljkas()
        }
    }

    suspend fun clearData() {
        withContext(Dispatchers.IO) {
            biljkaDao.clearData()
        }
    }

    suspend fun getImage(idBiljke: Long): BiljkaBitmap? {
        return withContext(Dispatchers.IO) {
            biljkaDao.getBiljkaBitmapById(idBiljke)
        }
    }

}