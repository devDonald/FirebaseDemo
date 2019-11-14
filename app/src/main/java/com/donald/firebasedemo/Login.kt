package com.donald.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Login : AppCompatActivity() {

    private lateinit var email:String
    private lateinit var password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val register:TextView = findViewById(R.id.tv_login_register)
        val et_email: EditText =findViewById(R.id.et_login_email)
        val et_password: EditText =findViewById(R.id.et_login_password)
        val bt_login:Button = findViewById(R.id.bt_login_submit)


        register.setOnClickListener(View.OnClickListener {
            val toRegister = Intent(this, Register::class.java)
            startActivity(toRegister)
            finish()
        })

        bt_login.setOnClickListener(View.OnClickListener {
            email = et_email.text.toString().trim()
            password = et_password.text.toString().trim()


            if (email.isEmpty()|| !email.contains(".")|| !email.contains("@")){
                et_email.setError("Incorrect Email")
            }else if (password.isEmpty()){
                et_password.setError("password empty")
            } else{

            }
        })

    }
}
