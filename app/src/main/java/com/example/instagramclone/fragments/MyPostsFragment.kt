package com.example.instagramclone.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramclone.Models.Post
import com.example.instagramclone.adapters.MyPostRvAdapter
import com.example.instagramclone.databinding.FragmentMyPostsBinding
import com.example.instagramclone.util.POST_FOLDER
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class MyPostsFragment : Fragment() {

 private lateinit var binding:FragmentMyPostsBinding
    private val postList=ArrayList<Post>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

  @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentMyPostsBinding.inflate(inflater, container, false)

        Firebase.firestore.collection(POST_FOLDER).document(Firebase.auth.currentUser!!.uid).get()


        val adapter=MyPostRvAdapter(requireContext(),postList)
        binding.recyclerView.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.adapter=adapter
 Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
     val tempList= arrayListOf<Post>()
     for(i in it.documents){
         val post:Post=i.toObject<Post>()!!
         tempList.add(post)
     }
     postList.addAll(tempList)
     adapter.notifyDataSetChanged()
 }


        return binding.root
    }

    companion object

}