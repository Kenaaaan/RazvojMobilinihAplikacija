package com.example.a19283spirala1

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrefleDAO() {
    private lateinit var context: Context
    constructor(context: Context) : this() {
        this.context=context
    }

    val soilTextureMap = mapOf(
        Zemljiste.SLJUNKOVITO to listOf("9"),
        Zemljiste.KRECNJACKO to listOf("10"),
        Zemljiste.GLINENO to listOf("1", "2"),
        Zemljiste.PJESKOVITO to listOf("3", "4"),
        Zemljiste.ILOVACA to listOf("5", "6"),
        Zemljiste.CRNICA to listOf("7", "8")
    )

    val klimatskiTipoviMap = mapOf(
        KlimatskiTip.SREDOZEMNA to Pair(6..9, 1..5),
        KlimatskiTip.TROPSKA to Pair(8..10, 7..10),
        KlimatskiTip.SUBTROPSKA to Pair(6..9, 5..8),
        KlimatskiTip.UMJERENA to Pair(4..7, 3..7),
        KlimatskiTip.SUHA to Pair(7..9, 1..2),
        KlimatskiTip.PLANINSKA to Pair(0..5, 3..7)
    )

    fun getLatinskoIme(naziv : String):String{
        val regex = Regex("\\(([^)]+)\\)")
        val matchResult = regex.find(naziv)
        return matchResult?.groups?.get(1)?.value ?: ""
    }



    suspend fun getImage(biljka: Biljka, biljkaRepository: BiljkaRepository): Bitmap {
        val defaultBitmap: Bitmap = BitmapFactory.decodeResource(context?.resources,
            R.drawable.images
        )
        return withContext(Dispatchers.IO) {
            try {
                val latinskiNaziv :String = getLatinskoIme(biljka.naziv)

                val response = ApiAdapter.retrofit.searchPlants(latinskiNaziv)
                if (response.isSuccessful) {

                    val responseBody = response.body()
                    if (responseBody != null && responseBody.data.isNotEmpty()) {

                        val imageUrl = responseBody.data[0].imageURL
                        if (imageUrl.isNotEmpty() && context != null) {

                            val bitmap = Glide.with(context!!)
                                .asBitmap()
                                .load(imageUrl as String)
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .submit()
                                .get()

                            biljkaRepository.addImage(biljka.id!!, bitmap)

                            bitmap
                        } else {
                            System.out.println("imageURL prazan")
                            defaultBitmap
                        }
                    } else {
                        defaultBitmap
                    }
                } else {
                    System.out.println("neuspjesan response")
                    defaultBitmap
                }
            } catch (e: Exception) {
                e.printStackTrace()
                defaultBitmap
            }
        }
    }

    suspend fun fixData(biljka:Biljka):Biljka{
        var novaBiljka:Biljka = biljka
        return withContext(Dispatchers.IO) {
            try {
                val latinskiNaziv :String = getLatinskoIme(biljka.naziv)
                val searchResponse = ApiAdapter.retrofit.searchPlants(latinskiNaziv)
                if (searchResponse.isSuccessful) {
                    val plantData = searchResponse.body()?.data?.firstOrNull()
                    if (plantData != null) {
                        val plantDetailsResponse = ApiAdapter.retrofit.getPlantById(plantData.id)
                        if (plantDetailsResponse.isSuccessful) {

                            val plantDetails = plantDetailsResponse.body()?.data

                            var porodica = biljka.family
                            var jela:List<String> = biljka.jela
                            var upozorenje = biljka.medicinskoUpozorenje



                            if (plantDetails != null) {
                                if(biljka.family != plantDetails.main_species.family){
                                    porodica = plantDetails.main_species.family
                                }


                                if (!plantDetails.main_species.edible) {
                                    jela = emptyList()
                                    if (!biljka.medicinskoUpozorenje.contains("NIJE JESTIVO")) {
                                        upozorenje += ", NIJE JESTIVO"
                                    }
                                }

                                if (plantDetails.main_species.specifications.toxicity != "none" &&
                                    plantDetails.main_species.specifications.toxicity != null &&
                                    !biljka.medicinskoUpozorenje.contains("TOKSIČNO")) {
                                    upozorenje += ", TOKSIČNO"
                                }

                                val fetchedSoilTexture = plantDetails.main_species.growth.soilTexture
                                System.out.println(fetchedSoilTexture.toString() + "ovo je soil texture")
                                var zemljisniTipovi: MutableList<Zemljiste> = emptyList<Zemljiste>().toMutableList()
                                if(fetchedSoilTexture != null) {

                                    zemljisniTipovi = biljka.zemljisniTipovi.filter { texture ->
                                        val validValues = soilTextureMap[texture]
                                        validValues?.contains(fetchedSoilTexture.toString()) == true
                                    }.toMutableList()


                                    for ((key, value) in soilTextureMap) {
                                        if (value.contains(fetchedSoilTexture.toString()) && !biljka.zemljisniTipovi.contains(
                                                key
                                            )
                                        ) {
                                            zemljisniTipovi.add(key)
                                        }
                                    }
                                }
                                val fetchedLight = plantDetails.main_species.growth.light
                                val fetchedHumidity = plantDetails.main_species.growth.atmosphericHumidity

                                val validKlimatskiTipovi = biljka.klimatskiTipovi.filter { klimatskiTip ->
                                    klimatskiTipoviMap.entries.any { (tip, range) ->
                                        val (lightRange, humidityRange) = range
                                        klimatskiTip == tip && fetchedLight in lightRange && fetchedHumidity in humidityRange
                                    }
                                }.toMutableList()
                                klimatskiTipoviMap.entries.forEach { (klimatskiTip, range) ->
                                    val (lightRange, humidityRange) = range
                                    if (fetchedLight in lightRange && fetchedHumidity in humidityRange) {
                                        if (!validKlimatskiTipovi.contains(klimatskiTip)) {
                                            validKlimatskiTipovi.add(klimatskiTip)
                                        }
                                    }
                                }
                                novaBiljka = Biljka(biljka.naziv, porodica, upozorenje,biljka.medicinskeKoristi, biljka.profilOkusa, jela, validKlimatskiTipovi, zemljisniTipovi)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            novaBiljka
        }
    }


    suspend fun getPlantsWithFlowerColor(flower_color:String,substr:String):List<Biljka>{
        val filteredPlants = mutableListOf<Biljka>()

        try {
            val response = ApiAdapter.retrofit.searchPlantsByColor(flower_color,substr)
            if (response.isSuccessful) {
                val plants = response.body()?.data ?: emptyList()

                for (plantData in plants) {
                    if (plantData.commonName?.contains(substr, ignoreCase = true) == true ||
                        plantData.scientificName?.contains(substr, ignoreCase = true) == true
                    ) {
                        var biljka = Biljka(plantData.commonName+"("+plantData.scientificName+")",
                            plantData.family,
                            "",
                            emptyList(),
                            null,
                            emptyList(),
                            emptyList(),
                            emptyList())
                        var ispravnaBiljka = fixData(biljka)
                        filteredPlants.add(ispravnaBiljka)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return filteredPlants.toList()
    }



}