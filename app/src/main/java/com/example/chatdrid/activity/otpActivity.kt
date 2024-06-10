package com.example.chatdrid.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatdrid.R
import com.example.chatdrid.databinding.ActivityOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class otpActivity : AppCompatActivity() {
    private  lateinit var  binding: ActivityOtpBinding
    private lateinit var mAuth:FirebaseAuth
    private lateinit var verificationId:String
    private lateinit var dialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        mAuth=FirebaseAuth.getInstance()
        val builder=AlertDialog.Builder(this)
        builder.setMessage("Requesting SMS instead....")
        builder.setTitle("Loading")
        builder.setCancelable(false)
        dialog=builder.create()
        dialog.show()

        val phoneNumber="+91"+intent.getStringExtra("number")
        val option=PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    Toast.makeText(this@otpActivity, "Some error occurred.. ${p0}", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)

                    dialog.dismiss()
                    verificationId=p0
                }

            }).build()



        PhoneAuthProvider.verifyPhoneNumber(option)
        binding.button.setOnClickListener{

            if (binding.pinview.text!!.isEmpty()){

                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
            else{
                dialog.show()
                val credential=PhoneAuthProvider.getCredential(verificationId,binding.pinview.text!!.toString())
                mAuth.signInWithCredential(credential)
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            dialog.dismiss()
                            startActivity(Intent(this,profileActivity::class.java))
                            finish()
                        }
                        else{
                            dialog.dismiss()
                            Toast.makeText(this, "Error ${it.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }
}