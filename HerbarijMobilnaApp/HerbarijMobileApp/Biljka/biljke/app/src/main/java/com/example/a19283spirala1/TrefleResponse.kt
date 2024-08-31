package com.example.a19283spirala1

import com.google.gson.annotations.SerializedName

data class TrefleResponse(
    @SerializedName("data") val data: List<BiljkaData>
)