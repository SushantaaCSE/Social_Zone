package com.example.chatdrid.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatdrid.MainActivity
import com.example.chatdrid.R
import com.example.chatdrid.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth=FirebaseAuth.getInstance()
        if (mAuth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        binding.button.setOnClickListener{
            if(binding.editPh.text!!.isEmpty()){
                Toast.makeText(this, "Enter your number", Toast.LENGTH_SHORT).show()
            }
            else{
                var intent=Intent(this,otpActivity::class.java)
                intent.putExtra("number",binding.editPh.text!!.toString())
                startActivity(intent)
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}