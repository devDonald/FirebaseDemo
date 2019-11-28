package com.donald.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.ChangeEventListener
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var adapter: FirebaseRecyclerAdapter<UserModel, UsersViewHolder>? = null

    private var firebaseDB: FirebaseDatabase? = null
    private var usersRef: DatabaseReference? = null
    private var usersList = mutableListOf<UserModel>()

    private lateinit var usersRecycler: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersRecycler = findViewById(R.id.all_users_recycler)

        val mLayoutManager = LinearLayoutManager(applicationContext)

        usersRecycler.layoutManager = mLayoutManager
        usersRecycler.itemAnimator = DefaultItemAnimator()

        firebaseDB = FirebaseDatabase.getInstance()
        usersRef = firebaseDB!!.reference!!.child("Users")



        loadNotesList()


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

    private fun loadNotesList() {


        val query = usersRef!!.limitToLast(8)

        adapter = object : FirebaseRecyclerAdapter<UserModel, UsersViewHolder>(
            UserModel::class.java, R.layout.all_users, UsersViewHolder::class.java, query
        ) {

            override fun populateViewHolder(
                viewHolder: UsersViewHolder?,
                model: UserModel?,
                position: Int
            ) {
                viewHolder!!.bindUsers(model)
            }

            override fun onChildChanged(
                type: ChangeEventListener.EventType,
                snapshot: DataSnapshot?,
                index: Int,
                oldIndex: Int
            ) {
                super.onChildChanged(type, snapshot, index, oldIndex)
                usersRecycler.scrollToPosition(index)
            }
        }

        usersRecycler.adapter=adapter
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
    }
}
