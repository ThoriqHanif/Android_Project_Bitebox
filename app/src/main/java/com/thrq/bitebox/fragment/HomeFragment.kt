package com.thrq.bitebox.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.thrq.bitebox.R
import com.thrq.bitebox.adapter.CategoryAdapter
import com.thrq.bitebox.adapter.FoodAdapter
import com.thrq.bitebox.databinding.FragmentHomeBinding
import com.thrq.bitebox.model.AddFoodModel
import com.thrq.bitebox.model.CategoryModel
import java.util.ArrayList

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        getCategories()
        getSliderImage()
        getFood()
        return binding.root

    }

    private fun getSliderImage() {
        Firebase.firestore.collection("slider").document("item")
            .get().addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.ivSlider)
            }
    }

    private fun getFood() {
        val list = ArrayList<AddFoodModel>()
        Firebase.firestore.collection("foods")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(AddFoodModel::class.java)
                    list.add(data!!)
                }
                binding.foodRecyle.adapter = FoodAdapter(requireContext(), list)
            }
    }

    private fun getCategories(){
        val list = ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                binding.categoryRecyle.adapter = CategoryAdapter(requireContext(), list)
            }
    }
}