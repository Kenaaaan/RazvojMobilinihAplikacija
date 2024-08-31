package com.example.a19283spirala1

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiAdapter {
    val retrofit : ApiKlasa = Retrofit.Builder()
        .baseUrl("https://trefle.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiKlasa::class.java)
}