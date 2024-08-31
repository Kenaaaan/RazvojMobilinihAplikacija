package com.example.a19283spirala1

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiKlasa {
    @GET("api/v1/plants/search")
    suspend fun searchPlants(
        @Query("q") query: String,
        @Query("token") token: String = "48uvFdZFGyQAX1KvmBS2iNGZIwt4j4VZIUCCLHGzUNs"
    ): Response<TrefleResponse>

    @GET("api/v1/plants/search")
    suspend fun searchPlantsByColor(
        @Query("filter[flower_color]") flowerColor: String,
        @Query("q") query: String,
        @Query("token") token: String = "48uvFdZFGyQAX1KvmBS2iNGZIwt4j4VZIUCCLHGzUNs"
    ): Response<TrefleResponse>

   @GET("api/v1/plants/{id}")
    suspend fun getPlantById(
        @Path("id") id: Long,
        @Query("token") token: String = "48uvFdZFGyQAX1KvmBS2iNGZIwt4j4VZIUCCLHGzUNs"
    ): Response<TrefleIDResponse>
}