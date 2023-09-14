package com.thrq.bitebox.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.thrq.bitebox.databinding.ActivityAddressBinding

class AddressActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddressBinding
    private lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = this.getSharedPreferences("user", MODE_PRIVATE)

        loadUserInfo()

        binding.btnCheckoutPros.setOnClickListener {
            validateData(
                binding.etNoHp.text.toString(),
                binding.etNama.text.toString(),
                binding.etProv.text.toString(),
                binding.etKota.text.toString(),
                binding.etKec.text.toString(),
                binding.etCode.text.toString()
            )
        }
    }

    private fun validateData(number: String, name: String, pinCode: String, kota: String, provinsi: String, desa: String) {

        if (number.isEmpty() || provinsi.isEmpty() || name.isEmpty()){
            Toast.makeText(this, "Tolong isi semua data", Toast.LENGTH_SHORT).show()
        }else{
            storeData(pinCode, kota, provinsi, desa)
        }

    }

    private fun storeData(pinCode: String, kota: String, provinsi: String, desa: String) {

        val map = hashMapOf<String, Any>()
        map["desa"] = desa
        map["kota"] = kota
        map["provinsi"] = provinsi
        map["pinCode"] = pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .update(map).addOnSuccessListener {
                startActivity(Intent(this, CheckoutActivity::class.java))
//                finish()

            }
            .addOnFailureListener {
                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }

    }

    private fun loadUserInfo() {
        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!! )
            .get().addOnSuccessListener {
                binding.etNoHp.setText(it.getString("userNoHp"))
                binding.etNama.setText(it.getString("userName"))
                binding.etProv.setText(it.getString("provinsi"))
                binding.etKota.setText(it.getString("kota"))
                binding.etKec.setText(it.getString("desa"))
                binding.etCode.setText(it.getString("pinCode"))
            }
            .addOnFailureListener {
                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }
    }
}