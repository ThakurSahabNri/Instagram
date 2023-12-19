package com.example.instagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.instagramclone.Models.User
import com.example.instagramclone.databinding.ActivityLogInBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class LogInActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLogInBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnLogIn.setOnClickListener{
            if(binding.tvEmail.editText?.text.toString().equals("") or
                binding.tvPassword.editText?.text.toString().equals("")){
                Toast.makeText(this@LogInActivity,"Please enter all credentials",Toast.LENGTH_SHORT)
                    .show()
            }else {
                 var user= User(binding.tvEmail.editText?.text.toString(),
                     binding.tvPassword.editText?.text.toString())
                Firebase.auth.signInWithEmailAndPassword(user.email!!,user.password!!).addOnCompleteListener{
                    if(it.isSuccessful){
                        startActivity(Intent(this@LogInActivity, HomeActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@LogInActivity,it.exception?.localizedMessage,Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        }
        binding.btnSignUp.setOnClickListener{
            startActivity(Intent(this@LogInActivity,SignUpActivity::class.java))
            finish()
        }
    }
}