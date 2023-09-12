package com.thrq.bitebox.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.thrq.bitebox.R
import com.thrq.bitebox.adapter.CategoryFoodAdapter
import com.thrq.bitebox.adapter.FoodAdapter
import com.thrq.bitebox.model.AddFoodModel
import java.util.ArrayList

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        getFoods(intent.getStringExtra("cate"))
    }

    private fun getFoods(category: String?) {
        val list = ArrayList<AddFoodModel>()
        Firebase.firestore.collection("foods").whereEqualTo("foodCategory", category)
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(AddFoodModel::class.java)
                    list.add(data!!)
                }
                val RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                RecyclerView.adapter = CategoryFoodAdapter(this, list)
            }
    }
}