package com.example.chatdrid.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatdrid.MainActivity
import com.example.chatdrid.R
import com.example.chatdrid.databinding.ActivityProfileBinding
import com.example.chatdrid.model.userModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class profileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var database:FirebaseDatabase
    private lateinit var selectedImage: Uri
    private lateinit var dialog: AlertDialog.Builder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog=AlertDialog.Builder(this)
            .setMessage("Updating Profile ")
            .setCancelable(false)
        database= FirebaseDatabase.getInstance()
        storage= FirebaseStorage.getInstance()
        mAuth=FirebaseAuth.getInstance()
        binding.userImage.setOnClickListener{
            val intent=Intent()
            intent.action=Intent.ACTION_GET_CONTENT
            intent.type="image/*"
           startActivityForResult(intent,1)
        }
        binding.buttonC.setOnClickListener{
            if (binding.edtName.text!!.isEmpty()){
                Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show()
            }
            else if (selectedImage==null){
                Toast.makeText(this, "select image", Toast.LENGTH_SHORT).show()
            }
            else{
                uploaddata()
            }
        }




        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun uploaddata() {
        val reference=storage.reference.child("profile").child(Date().time.toString())
        reference.putFile(selectedImage).addOnCompleteListener{
            if(it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener {task->
                    uploadInfo(task.toString())

                }
            }
        }
    }

    private fun uploadInfo(imgUrl: String){
        val user=userModel(mAuth.uid.toString(),binding.edtName.text.toString(),mAuth.currentUser!!.phoneNumber.toString(),imgUrl)
        database.reference.child("users")
            .child(mAuth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                startActivity(Intent(this,MainActivity::class.java))
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null){
            if (data.data!=null){
                selectedImage=data.data!!
                binding.userImage.setImageURI(selectedImage)
            }
        }
    }
}