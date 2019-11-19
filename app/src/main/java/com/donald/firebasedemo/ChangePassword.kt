package com.donald.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.Task
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast


class ChangePassword : AppCompatActivity() {
    private lateinit var new_password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val et_new_password: EditText = findViewById(R.id.et_cp_newpassword)

        val bt_update : Button = findViewById(R.id.bt_cp_submit)


        bt_update.setOnClickListener(View.OnClickListener {
            new_password = et_new_password.text.toString().trim()

            if (!new_password.isEmpty()){

                val user = FirebaseAuth.getInstance().currentUser

                user!!.updatePassword(new_password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this,"Password Updated Successfully",Toast.LENGTH_LONG).show()

                            updateUI()
                        }
                    }

            }
        })
    }

    private fun updateUI() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}
