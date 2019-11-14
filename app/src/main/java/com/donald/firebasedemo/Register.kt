package com.donald.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import javax.xml.namespace.NamespaceContext

class Register : AppCompatActivity() {
    private lateinit var names:String
    private lateinit var email:String
    private lateinit var phone:String
    private lateinit var password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val login: TextView = findViewById(R.id.tv_reg_login)
        val et_name:EditText =findViewById(R.id.et_reg_username)
        val et_email:EditText =findViewById(R.id.et_reg_email)
        val et_phone:EditText =findViewById(R.id.et_reg_phone)
        val et_password:EditText =findViewById(R.id.et_reg_password)
        val bt_register:Button = findViewById(R.id.bt_reg_submit)


        login.setOnClickListener(View.OnClickListener {
            val toLogin = Intent(this, Login::class.java)
            startActivity(toLogin)
            finish()
        })

        bt_register.setOnClickListener(View.OnClickListener {
            names = et_name.text.toString().trim()
            email = et_email.text.toString().trim()
            phone = et_phone.text.toString().trim()
            password = et_password.text.toString().trim()


            if (names.isEmpty()){
                et_name.setError("Name is empty")
            } else if (email.isEmpty()|| !email.contains(".")|| !email.contains("@")){
                et_email.setError("Incorrect Email")
            } else if (phone.isEmpty() || !phone.startsWith("0")){
                et_phone.setError("incorrect or empty phone no")
            } else if (password.isEmpty()){
                et_password.setError("password empty")
            } else{

            }

        })
    }
}
