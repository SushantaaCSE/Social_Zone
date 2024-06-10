package com.example.chatdrid

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.ViewParentCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.chatdrid.User_InterFace.ChannelsFragment
import com.example.chatdrid.User_InterFace.ChatFragment
import com.example.chatdrid.activity.LoginActivity
import com.example.chatdrid.adapter.viewPager
import com.example.chatdrid.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)




        enableEdgeToEdge()
        setContentView(binding!!.root)
        mAuth=FirebaseAuth.getInstance()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val fragmentArrayList=ArrayList<Fragment>()
        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(ChannelsFragment())
        if (mAuth.currentUser==null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

       val adapter= viewPager(this,supportFragmentManager,fragmentArrayList)
        binding!!.ViewPager.adapter=adapter
        binding!!.tab.setupWithViewPager(binding!!.ViewPager)


    }
}