package com.example.instagramclone.fragments

import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.Models.User
import com.example.instagramclone.R
import com.example.instagramclone.adapters.SearchAdapter
import com.example.instagramclone.databinding.FragmentSearchBinding
import com.example.instagramclone.util.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class SearchFragment : Fragment() {
     lateinit var binding:FragmentSearchBinding
  lateinit var adapter: SearchAdapter
  private var userList=ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSearchBinding.inflate(inflater, container, false)

        adapter= SearchAdapter(requireContext(),userList)
        binding.rv.layoutManager=LinearLayoutManager(requireContext())
        binding.rv.adapter=adapter


        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            val tempList=ArrayList<User>()
            userList.clear()
            for(i in it.documents){
                if(i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){

                }else {
                    val user = i.toObject<User>()!!
                    tempList.add(user)
                }
            }
            userList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        binding.searchButton.setOnClickListener{

            val text=binding.searchbar.text.toString()

         Firebase.firestore.collection(USER_NODE).whereEqualTo("name",text).get().addOnSuccessListener{
             val tempList=ArrayList<User>()
             userList.clear()

             if(it.isEmpty){

             }else{
                 for(i in it.documents){
                     if(i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){

                     }else {
                         val user = i.toObject<User>()!!
                         tempList.add(user)
                     }
                 }
                 userList.addAll(tempList)
                 adapter.notifyDataSetChanged()
             }

         }

        }

     return  binding.root
    }

    companion object {

    }
}