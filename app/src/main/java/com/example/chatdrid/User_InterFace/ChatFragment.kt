package com.example.chatdrid.User_InterFace

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatdrid.R
import com.example.chatdrid.adapter.ChatAdapter
import com.example.chatdrid.databinding.FragmentChatBinding
import com.example.chatdrid.model.userModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentChatBinding
    private var database:FirebaseDatabase?=null
    lateinit var userList:ArrayList<userModel>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentChatBinding.inflate(layoutInflater)
        database= FirebaseDatabase.getInstance()
        userList= ArrayList()


        database!!.reference.child("users")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()
                    for (snapshot1 in snapshot.children){
                        val user=snapshot1.getValue(userModel::class.java)
                        if (user!!.uid!=FirebaseAuth.getInstance().uid){
                            userList.add(user)
                        }

                    }
                    binding.userListRV.adapter=ChatAdapter(requireContext(),userList)
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        return binding.root
    }

}
