package com.example.aichatbot

data class Message(
    val message : String,
    val SENT_BY_ME : String = "me",
    val SENT_BY_BOT : String = "bot",
    val sentBy : String = ""
)

