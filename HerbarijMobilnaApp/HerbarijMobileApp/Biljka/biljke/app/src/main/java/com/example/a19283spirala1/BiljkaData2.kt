package com.example.a19283spirala1

import com.google.gson.annotations.SerializedName

data class BiljkaData2(
    @SerializedName("id") val id: Long,
    @SerializedName("common_name") val commonName: String?,
    @SerializedName("scientific_name") val scientificName: String,
    @SerializedName("family_common_name") val familyCommonName: String?,
    @SerializedName("image_url") val imageURL: String?,
    @SerializedName("main_species") val main_species: klasaKojaJeMainSpecies
)