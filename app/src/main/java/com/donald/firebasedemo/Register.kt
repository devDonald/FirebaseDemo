package com.donald.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kaopiz.kprogresshud.KProgressHUD
import javax.xml.namespace.NamespaceContext

class Register : AppCompatActivity() {
    private lateinit var names:String
    private lateinit var email:String
    private lateinit var phone:String
    private lateinit var password:String
    private var mAuth: FirebaseAuth? = null
    private var mDatabase:FirebaseDatabase? = null
    private lateinit var hud: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        mAuth = FirebaseAuth.getInstance()

        mDatabase = FirebaseDatabase.getInstance()

        val mUsers: DatabaseReference =mDatabase!!.reference!!.child("Users")


        val login: TextView = findViewById(R.id.tv_reg_login)
        val et_name:EditText =findViewById(R.id.et_reg_username)
        val et_email:EditText =findViewById(R.id.et_reg_email)
        val et_phone:EditText =findViewById(R.id.et_reg_phone)
        val et_password:EditText =findViewById(R.id.et_reg_password)
        val bt_register:Button = findViewById(R.id.bt_reg_submit)

       hud= KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setDetailsLabel("Creating User")
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)


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

                hud.show()

                mAuth!!.createUserWithEmailAndPassword(email,password)


                    .addOnCompleteListener(this){task ->

                        hud.dismiss()

                        if (task.isSuccessful){

                            val userId = mAuth!!.currentUser!!.uid

                            //update user profile information
                            val currentUserDb = mUsers!!.child(userId)
                            currentUserDb.child("names").setValue(names)
                            currentUserDb.child("phone").setValue(phone)
                            currentUserDb.child("email").setValue(email)

                            updateUserInfoAndUI()

                        } else{

                            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()


                        }
                    }
            }

        })
    }


    private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
