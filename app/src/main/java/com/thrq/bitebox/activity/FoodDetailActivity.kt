package com.thrq.bitebox.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.thrq.bitebox.MainActivity
import com.thrq.bitebox.R
import com.thrq.bitebox.databinding.ActivityFoodDetailBinding
import com.thrq.bitebox.roomdb.AppDatabase
import com.thrq.bitebox.roomdb.FoodDao
import com.thrq.bitebox.roomdb.FoodModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFoodDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        binding = ActivityFoodDetailBinding.inflate(layoutInflater)

        getFooodDetails(intent.getStringExtra("id"))
        setContentView(binding.root)
    }

    private fun getFooodDetails(foodId: String?) {
        Firebase.firestore.collection("foods")
            .document(foodId!!).get().addOnSuccessListener {
                val list = it.get("foodImages") as ArrayList<String>
                val name = it.getString("foodName")
                val foodPrice = it.getString("foodPrice")
                val foodDesc = it.getString("foodDesc")
                binding.tvNama.text = name
                binding.tvHarga.text = foodPrice
                binding.tvDesc.text = foodDesc
                binding.tvHarga2.text = foodPrice

                val slideList = ArrayList<SlideModel>()
                for (data in list){
                    slideList.add(SlideModel(data, ScaleTypes.FIT))
                }

                cartAction(foodId, name, foodPrice, it.getString("foodCoverImg"))

                binding.slider.setImageList(slideList)
            } .addOnFailureListener {
                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }
    }

    private fun cartAction(foodId: String, name: String?, foodPrice: String?, coverImg: String?) {

        val foodDao = AppDatabase.getInstance(this).foodDao()

        if (foodDao.isExit(foodId) != null){
            binding.tvAddtoCart.text = "Go to Cart"
        }else{
            binding.tvAddtoCart.text = "Add to Cart"
        }

        binding.tvAddtoCart.setOnClickListener{
            if (foodDao.isExit(foodId)!= null){
                openCart()
            }else{
                addToCart(foodDao, foodId, name, foodPrice, coverImg)
            }
        }
    }

    private fun addToCart(foodDao: FoodDao, foodId: String, name: String?, foodPrice: String?, coverImg: String?) {
        val data = FoodModel(foodId, name, coverImg, foodPrice)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                foodDao.insertFood(data)
                binding.tvAddtoCart.text = "Go to Cart"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun openCart() {
        val preference = this.getSharedPreferences("info", MODE_PRIVATE)
        val  editor = preference.edit()
        editor.putBoolean("isCart", true)
        editor.apply()



        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}