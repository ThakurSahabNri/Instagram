package com.example.instagramclone.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.databinding.SearchRvBinding
import com.example.instagramclone.util.FOLLOW
import com.example.instagramclone.util.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore

class SearchAdapter(var context: Context, private var userList:ArrayList<com.example.instagramclone.Models.User>):RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(val binding:SearchRvBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=SearchRvBinding.inflate(LayoutInflater.from(context),parent,false)
         return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var isFollow=false
        Glide.with(context).load(userList[position].image).placeholder(R.drawable.profile).
        into(holder.binding.profileImage)
      holder.binding.name.text=userList[position].name
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).whereEqualTo("email",userList[position].email).get().
            addOnSuccessListener {
                if(it.documents.size==0){
                   isFollow=false
                }else{
                    holder.binding.follow.text="Unfollow"
                    isFollow=true

                }
            }

         holder.binding.follow.setOnClickListener{
             if(isFollow){

                 Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).whereEqualTo("email",userList[position].email).get().
                 addOnSuccessListener {
                     Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).document(it.documents.get(0).id).delete()
                     holder.binding.follow.text= "Follow"
                     isFollow=false
                 }
             }else{
                 Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+FOLLOW).document().set(userList[position])
                 holder.binding.follow.text= "Unfollow"
                 isFollow=true
             }


        }
    }
}