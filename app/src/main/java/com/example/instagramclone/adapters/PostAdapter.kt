package com.example.instagramclone.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.Models.Post
import com.example.instagramclone.Models.User
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ItemPostHomeBinding
import com.example.instagramclone.util.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostAdapter(var context: Context, private var postList:ArrayList<Post>):RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(var binding:ItemPostHomeBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ItemPostHomeBinding.inflate(LayoutInflater.from(context),parent,false)
        return  ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList[position].uid).get()
                .addOnSuccessListener {
                    val user = it.toObject<User>()!!
                    Glide.with(context).load(user.image).placeholder(R.drawable.profile)
                        .into(holder.binding.profileImage)
                    holder.binding.userName.text = user.name
                }
        } catch (e: Exception) {

        }

        try {
            val text = TimeAgo.using(postList[position].time.toLong())
            holder.binding.time.text = text
        }catch (e:Exception){
            holder.binding.time.text = ""
        }

        Glide.with(context).load(postList[position].imageUrl).placeholder(R.drawable.profile)
            .into(holder.binding.imageView)

        holder.binding.ivShare.setOnClickListener{
            val i=Intent(Intent.ACTION_SEND)
            i.type="text/plain"
            i.putExtra(Intent.EXTRA_TEXT,postList[position].imageUrl)
            context.startActivity(i)

        }
        holder.binding.description.text = postList[position].caption
        holder.binding.ivLike.setOnClickListener {
            holder.binding.ivLike.setImageResource(R.drawable.heart_like)
        }
    }


}