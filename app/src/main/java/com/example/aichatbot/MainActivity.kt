package com.example.aichatbot

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView : RecyclerView
    lateinit var welcomeTextView : TextView
    lateinit var messageEditText : EditText
    lateinit var sendButton : ImageButton
    lateinit var messageAdapter: MessageAdapter
    lateinit var linearLayoutManager: LinearLayoutManager


    private val messageList = ArrayList<Message>()
    private val JSON : MediaType = "application/json; charset=utf-8".toMediaType()
    private val API_KEY = "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"
    private val okHttpClient : OkHttpClient = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        welcomeTextView = findViewById(R.id.welcome_text)
        messageEditText = findViewById(R.id.message_edit_text)
        sendButton = findViewById(R.id.send_btn)

        //setup recycler view
        messageAdapter = MessageAdapter(messageList)
        recyclerView.adapter = messageAdapter
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager


        sendButton.setOnClickListener {
            val question = messageEditText.text.toString().trim()
            addToChat(question,Message(question).SENT_BY_ME)
            messageEditText.setText("")
            callApi(question)
            welcomeTextView.visibility = View.GONE
        }

    }

    private fun addToChat(message : String, sentBy : String) {
        runOnUiThread {
            messageList.add(Message(message, sentBy = sentBy))
            messageAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
        }
    }
    fun addResponce(response : String){
        messageList.removeAt(messageList.size - 1)
        addToChat(response,Message(response).SENT_BY_BOT)
    }

    fun callApi(question : String){
        messageList.add(Message("Typing...",Message(question).SENT_BY_BOT))
        val jsonBody = JSONObject()
        jsonBody.put("model","text-davinci-003")
        jsonBody.put("prompt",question)
        jsonBody.put("max_tokens",4000)
        jsonBody.put("temperature",0)

        val body : RequestBody = jsonBody.toString()
            .toRequestBody(JSON)
        val request : Request = Request.Builder()
            .url("https://api.openai.com/v1/completions")
            .header("Authorization","Bearer $API_KEY")
            .post(body)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                addResponce("Failure due to " + e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    val jsonObject: JSONObject?
                    try {
                        jsonObject = JSONObject(response.body!!.string())
                        val jsonArray = jsonObject.getJSONArray("choices")
                        val result = jsonArray.getJSONObject(0)?.getString("text")
                        addResponce(result!!.trim())
                    }catch (e : JSONException){
                        print(e.message.toString())
                    }
                } else {
                    addResponce("Failure to Load Response due to" + response.body.toString())
                }
            }

        })
    }
}