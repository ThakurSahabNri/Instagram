package com.example.instagramclone.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.Models.Post
import com.example.instagramclone.Models.User
import com.example.instagramclone.R
import com.example.instagramclone.adapters.FollowAdapter
import com.example.instagramclone.adapters.PostAdapter
import com.example.instagramclone.databinding.FragmentHomeBinding
import com.example.instagramclone.util.FOLLOW
import com.example.instagramclone.util.POST
import com.example.instagramclone.util.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {
    lateinit var binding:FragmentHomeBinding
    private var postlist= ArrayList<Post>()
    private var followList=ArrayList<User>()
    lateinit var followAdapter: FollowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentHomeBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar)
        setHasOptionsMenu(true)

        val adapter=PostAdapter(requireContext(),postlist)
        val layoutManager=LinearLayoutManager(context)
        layoutManager.orientation=LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager=layoutManager
        binding.recyclerView.adapter=adapter

        followAdapter=FollowAdapter(requireContext(),followList)
        binding.followRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.followRv.adapter=followAdapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).get().addOnSuccessListener {
            val tempList= ArrayList<User>()
           followList.clear()
            for(i in it.documents){
                val follow: User =i.toObject<User>()!!
                tempList.add(follow)
            }
            followList.addAll(tempList)
            followAdapter.notifyDataSetChanged()
        }


        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            val tempList= ArrayList<Post>()
            postlist.clear()
            for(i in it.documents){
                val post: Post =i.toObject<Post>()!!
                tempList.add(post)
            }
            postlist.addAll(tempList)
            adapter.notifyDataSetChanged()
        }


        return  binding.root
    }

    companion object {

    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User =it.toObject<User>()!!
                if(!user.image.isNullOrEmpty()){
                    Picasso.get().load(user.image).into(binding.profileImage)
                }
            }
    }
}