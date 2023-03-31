package com.example.aichatbot

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ApiServiceModel(
    @SerialName("model")
    val model: String,
    @SerialName("prompt")
    val prompt: String,
    @SerialName("max_tokens")
    val maxTokens: Int,
    @SerialName("temperature")
    val temp: Int
)

@kotlinx.serialization.Serializable
data class ResponseModel(
    @SerialName("choices")
    val choice: List<Choice>
)
@kotlinx.serialization.Serializable
data class Choice(
    @SerialName("text")
    val text : String,
)