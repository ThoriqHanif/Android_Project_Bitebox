package com.thrq.bitebox.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.thrq.bitebox.R
import com.thrq.bitebox.databinding.ActivityRegisterBinding
import com.thrq.bitebox.model.UserModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnlogin.setOnClickListener {
            OpenLogin()
        }

        binding.btnSignUp.setOnClickListener {
            validateUser()
        }
    }

    private fun validateUser() {
        if (binding.teNama.text!!.isEmpty() || binding.teNoHp.text!!.isEmpty())
            Toast.makeText(this, "Tolong lengkapi data", Toast.LENGTH_SHORT).show()
        else
            storeData()
    }

    private fun storeData() {
        val builder =  AlertDialog.Builder(this)
            .setTitle("Loading..")
            .setMessage("Mohon menunggu")
            .setCancelable(false)
            .create()
        builder.show()


        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putString("number", binding.teNoHp.text.toString())
        editor.putString("name",binding.teNama.text.toString())
        editor.apply()


        val data = UserModel(userName = binding.teNama.text.toString(), userNoHp = binding.teNoHp.text.toString())

        Firebase.firestore.collection("users").document(binding.teNoHp.text.toString())
            .set(data).addOnSuccessListener {
                Toast.makeText(this, "Berhasil Registrasi", Toast.LENGTH_SHORT).show()
                builder.dismiss()
                OpenLogin()

            }
            .addOnFailureListener {
                builder.dismiss()
                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }
    }

    private fun OpenLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}