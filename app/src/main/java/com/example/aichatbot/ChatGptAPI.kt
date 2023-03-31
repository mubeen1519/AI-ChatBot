package com.example.aichatbot

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatGptAPI {

    @Headers("Bearer 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")
    @POST("/completions")
    suspend fun getChat(): Response<ApiServiceModel>
}

object ApiHelper {
    val baseUrl = "https://api.openai.com/v1"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

}