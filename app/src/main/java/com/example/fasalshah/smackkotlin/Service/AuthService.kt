package com.example.fasalshah.smackkotlin.Service

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.fasalshah.smackkotlin.Utils.UrlLogin
import com.example.fasalshah.smackkotlin.Utils.UrlRegister
import org.json.JSONException
import org.json.JSONObject

object AuthService {

    var user = ""
    var authToken = ""
    var isLogin = false

    fun registerUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit) {

        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()

        val registerRequest = object : StringRequest(Method.POST, UrlRegister, Response.Listener { response ->
            println(response)
            complete(true)

        },
                Response.ErrorListener { error ->
                    Log.e("register error", error.localizedMessage)
                    complete(false)
                }

        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(registerRequest)
    }


    //login
    fun loginUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit) {

        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()

        val loginRequest = object : JsonObjectRequest(Method.POST, UrlLogin, null, Response.Listener { response ->
            println(response)
            try {
                user = response.getString("user")
                authToken = response.getString("token")
                isLogin = true
                complete(true)
            } catch (e: JSONException) {
                Log.d("JSON", e.localizedMessage)
            }


        },
                Response.ErrorListener { error ->
                    Log.e("register error", error.localizedMessage)
                    complete(false)
                }

        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(loginRequest)
    }
}