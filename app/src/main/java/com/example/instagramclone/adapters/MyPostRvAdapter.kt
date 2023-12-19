package com.example.instagramclone.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Models.Post
import com.example.instagramclone.databinding.MyPostRvBinding
import com.squareup.picasso.Picasso

class MyPostRvAdapter(var context:Context, private var postList:ArrayList<Post>) : RecyclerView.Adapter<MyPostRvAdapter.ViewHolder>(){

    inner class ViewHolder(var binding:MyPostRvBinding):
        RecyclerView.ViewHolder(binding.root)


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding= MyPostRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      Picasso.get().load(postList[position].imageUrl).into(holder.binding.iv)
    }

    override fun getItemCount(): Int {
    return postList.size
    }




}


