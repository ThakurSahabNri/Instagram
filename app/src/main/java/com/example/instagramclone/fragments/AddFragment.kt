package com.example.instagramclone.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramclone.Post.PostActivity
import com.example.instagramclone.Post.ReelsActivity
import com.example.instagramclone.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddFragment : BottomSheetDialogFragment() {
    lateinit var binding:FragmentAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentAddBinding.inflate(inflater, container, false)

        binding.addPost.setOnClickListener{
            val intent=Intent(requireContext(),PostActivity::class.java)
           activity?.startActivity(intent)
//            activity?.finish()
        }
        binding.uploadReel.setOnClickListener{
            val intent=Intent(requireContext(), ReelsActivity::class.java)
            activity?.startActivity(intent)
//            activity?.finish()
        }


        return binding.root
    }

    companion object

}