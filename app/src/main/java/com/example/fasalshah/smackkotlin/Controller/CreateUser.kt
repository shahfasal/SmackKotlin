package com.example.fasalshah.smackkotlin.Controller

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast
import com.example.fasalshah.smackkotlin.R
import com.example.fasalshah.smackkotlin.Service.AuthService
import com.example.fasalshah.smackkotlin.Service.UserDataService
import com.example.fasalshah.smackkotlin.Utils.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUser : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5,0.5,0.5,1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        register_progressBar.visibility = View.INVISIBLE
    }

    fun generateAvatarClicked(view: View) {

        val random = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        if (color == 0) {
            userAvatar = "light$avatar"
        } else {
            userAvatar = "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        register_avatar_btn.setImageResource(resourceId)
    }

    fun generateColorClicked(view: View) {
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        register_avatar_btn.setBackgroundColor(Color.rgb(r, g, b))

        val saveR = r.toDouble() / 255
        val saveG = g.toDouble() / 255
        val saveB = b.toDouble() / 255


        avatarColor = "[$saveR,$saveG,$saveB,1]"

    }

    fun createUserClicked(view: View) {
        enableSpinner(true)
        val user = register_et_name.text.toString()
        val email = register_et_email.text.toString()
        val password = register_et_password.text.toString()

        if (user.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

            AuthService.registerUser(this, email, password) { registerSuccess ->
                if (registerSuccess) {
                    AuthService.loginUser(this, email, password) { loginSuccess ->
                        if (loginSuccess) {
                            println(AuthService.authToken)
                            AuthService.createUser(this, user, email, userAvatar, avatarColor) { createSuccess ->
                                if (createSuccess) {
                                    val broadcasrIntent = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(broadcasrIntent)
                                    enableSpinner(false)
                                    finish()

                                } else {
                                    error()
                                }
                            }
                        } else {
                            error()
                        }
                    }
                } else {
                    error()
                }
            }
        }else{
            Toast.makeText(this,"Fill all the fields",Toast.LENGTH_LONG).show()
            enableSpinner(false)
        }
    }

    fun error(){
        Toast.makeText(this,"Something went wrong!Try again",Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun enableSpinner(boolean: Boolean){

        if(boolean){
            register_progressBar.visibility = View.VISIBLE
        }else{
            register_progressBar.visibility = View.INVISIBLE

        }
            register_avatar_btn.isEnabled = !boolean
            register_btn_color.isEnabled = !boolean
            register_btn_create_user.isEnabled = !boolean
    }
}
