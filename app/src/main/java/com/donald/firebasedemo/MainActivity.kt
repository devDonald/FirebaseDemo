package com.donald.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.logout_file,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem)= when(item.itemId){

        R.id.user_logout -> {
            logout()
            true
        }
        R.id.user_profile -> {
            val toprofile = Intent(this, UserProfile::class.java)
            startActivity(toprofile)
            true
        }
        R.id.user_change_password -> {
            val tochangepassword = Intent(this, ChangePassword::class.java)
            startActivity(tochangepassword)
            true
        }
        else->{
            super.onOptionsItemSelected(item)
        }
    }

    fun logout(){
        val mAuth:FirebaseAuth = FirebaseAuth.getInstance()
        mAuth!!.signOut()
        updateUserInfoAndUI()
    }

    private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}
