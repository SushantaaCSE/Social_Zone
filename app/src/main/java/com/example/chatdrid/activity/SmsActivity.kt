package com.example.chatdrid.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatdrid.R
import com.example.chatdrid.adapter.smsAdapter
import com.example.chatdrid.databinding.ActivitySmsBinding
import com.example.chatdrid.model.smsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Date


class SmsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySmsBinding

    private lateinit var senderUid:String
    private lateinit var receiverUid:String
    private lateinit var receiverBlock:String
    private lateinit var senderBlock:String
    private lateinit var database: FirebaseDatabase
    private lateinit var list:ArrayList<smsModel>





    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivitySmsBinding.inflate(layoutInflater)


        database= FirebaseDatabase.getInstance()
        senderUid=FirebaseAuth.getInstance().uid.toString()

        receiverUid=intent.getStringExtra("uid")!!
        list=ArrayList()
        senderBlock=senderUid+receiverUid
        receiverBlock=receiverUid+senderUid

        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        binding.sentSms.setOnClickListener{
           val message=smsModel(Date().time,binding.edtChat.text.toString(),senderUid)
            val randomKey=database.reference.push().key
            database.reference.child("chats")
                .child(senderBlock).child("message").child(randomKey!!).setValue(message).addOnSuccessListener {
                    database.reference.child("chats").child(receiverBlock).child("message").child(randomKey!!).setValue(message).addOnSuccessListener {
                        binding.edtChat.text=null
                    }
                }
        }
        database.reference.child("chats").child(senderBlock).child("message").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               list.clear()
                for(snapshot1 in snapshot.children){
                    val data=snapshot1.getValue(smsModel::class.java)
                    list.add(data!!)
                }
                binding.recyclerView.adapter=smsAdapter(this@SmsActivity,list)
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



    }
}