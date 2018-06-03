package com.example.fasalshah.smackkotlin.Service

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.fasalshah.smackkotlin.Utils.UrlCreateUser
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
           if(response!=null) {
               println(response)
               complete(true)
           }

        },
                Response.ErrorListener { error ->
                    try {
                        Log.e("register error", error.localizedMessage)

                    }catch (e : Exception){
                        e.printStackTrace()
                    }
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


    //login
    fun createUser(context: Context,name: String, email: String, avatarName:String, avatarColor: String, complete: (Boolean) -> Unit) {

        val jsonBody = JSONObject()
        jsonBody.put("name", name)
        jsonBody.put("email", email)
        jsonBody.put("avatarName",avatarName)
        jsonBody.put("avatarColor",avatarColor)
        val requestBody = jsonBody.toString()

        val createRequest = object : JsonObjectRequest(Method.POST, UrlCreateUser, null, Response.Listener { response ->
            println(response)
            try {
                UserDataService.name = response.getString("name")
                UserDataService.email = response.getString("email")
                UserDataService.avatarName = response.getString("avatarName")
                UserDataService.avatarColor = response.getString("avatarColor")
                UserDataService.id = response.getString("_id")
                complete(true)
            } catch (e: JSONException) {
                Log.d("JSON", e.localizedMessage)
            }


        },
                Response.ErrorListener { error ->
                    Log.e("create user error", error.localizedMessage)
                    complete(false)
                }

        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers.put("Authorization","Bearer $authToken")
                return headers
            }
        }

        Volley.newRequestQueue(context).add(createRequest)
    }
















}