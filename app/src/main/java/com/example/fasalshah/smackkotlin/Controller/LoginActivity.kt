package com.example.fasalshah.smackkotlin.Controller


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.fasalshah.smackkotlin.R


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


    fun loginLoginBtn(view : View){

    }

    fun signupBtn(view : View){
        val createUser = Intent(this, CreateUser::class.java)
        startActivity(createUser)
    }

}
