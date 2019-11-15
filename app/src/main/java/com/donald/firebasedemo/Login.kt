package com.donald.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kaopiz.kprogresshud.KProgressHUD

class Login : AppCompatActivity() {

    private lateinit var email:String
    private lateinit var password:String
    private var mAuth: FirebaseAuth? = null

    private lateinit var hud: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val register:TextView = findViewById(R.id.tv_login_register)
        val et_email: EditText =findViewById(R.id.et_login_email)
        val et_password: EditText =findViewById(R.id.et_login_password)
        val bt_login:Button = findViewById(R.id.bt_login_submit)


        mAuth = FirebaseAuth.getInstance()

        hud= KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setDetailsLabel("Authenticating User")
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)

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

                hud.show()

                mAuth!!.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener (this){task ->
                        hud.dismiss()
                        if (task.isSuccessful){

                            updateUI()
                        } else{
                            Toast.makeText(this, "Authentication failed.",Toast.LENGTH_LONG).show()
                        }
                    }
            }
        })

    }

    private fun updateUI() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
