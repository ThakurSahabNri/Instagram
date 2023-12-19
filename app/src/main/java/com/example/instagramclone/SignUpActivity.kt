package com.example.instagramclone

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.Models.User
import com.example.instagramclone.databinding.ActivitySignUpBinding
import com.example.instagramclone.util.USER_NODE
import com.example.instagramclone.util.USER_PROFILE_FOLDER
import com.example.instagramclone.util.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso


class SignUpActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    lateinit var user:User
    private val launcher=registerForActivityResult(ActivityResultContracts.GetContent()){
        uri->
        uri?.let{
         uploadImage(uri, USER_PROFILE_FOLDER){
           if(it!=null){
               user.image=it
               binding.profileImage.setImageURI(uri)
           }
          }
        }
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val text="<font color=#FF000000>Already have an account</font>"+"<font color=#1E88E5> LogIn ?</font>"
        binding.txtLogIn.text = Html.fromHtml(text)

        user= User()

        if(intent.hasExtra("MODE")){
            if(intent.getIntExtra("MODE",-1)==1){
                binding.btnSignUp.text="Update Profile"
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                        user =it.toObject<User>()!!

                        binding.textFieldName.editText?.setText(user.name)
                        binding.textFieldEmail.editText?.setText(user.email)
                        binding.textFieldPassword.editText?.setText(user.password)


                        if(!user.image.isNullOrEmpty()){
                            Picasso.get().load(user.image).into(binding.profileImage)
                        }
                    }
            }

        }
        binding.btnSignUp.setOnClickListener{
         if(intent.hasExtra("MODE")){
            if(intent.getIntExtra("MODE",-1)==1) {
                Firebase.firestore.collection(USER_NODE)
                    .document(Firebase.auth.currentUser!!.uid).set(user)
                    .addOnSuccessListener {
                        startActivity(
                            Intent(
                                this@SignUpActivity,
                                HomeActivity::class.java
                            )
                        )
                        finish()
                    }
            }
            }else {
             if (binding.textFieldName.editText?.text.toString().equals("") or
                 binding.textFieldEmail.editText?.text.toString().equals("") or
                 binding.textFieldPassword.editText?.text.toString().equals("")
             ) {
                 Toast.makeText(this, "Please fill all credentials", Toast.LENGTH_SHORT).show()
             } else {
                 FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                     binding.textFieldEmail.editText?.text.toString(),
                     binding.textFieldPassword.editText?.text.toString()
                 ).addOnCompleteListener { result ->
                     if (result.isSuccessful) {
                         user.name = binding.textFieldName.editText?.text.toString()
                         user.email = binding.textFieldEmail.editText?.text.toString()
                         user.password = binding.textFieldPassword.editText?.toString()
                         Firebase.firestore.collection(USER_NODE)
                             .document(Firebase.auth.currentUser!!.uid).set(user)
                             .addOnSuccessListener {
                                 startActivity(
                                     Intent(
                                         this@SignUpActivity,
                                         HomeActivity::class.java
                                     )
                                 )
                                 finish()
                             }

                     } else {
                         Toast.makeText(
                             this,
                             result.exception?.localizedMessage,
                             Toast.LENGTH_SHORT
                         ).show()
                     }
                 }
             }
         }
        }
        binding.profileImage.setOnClickListener{
            launcher.launch("image/*")
        }
        binding.txtLogIn.setOnClickListener{
            startActivity(Intent(this@SignUpActivity,LogInActivity::class.java))
            finish()
        }

    }
}