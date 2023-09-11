package com.thrq.bitebox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thrq.bitebox.databinding.LayoutFoodItemBinding
import com.thrq.bitebox.model.AddFoodModel

class FoodAdapter(val context: Context, val list : ArrayList<AddFoodModel>)
    : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(val binding: LayoutFoodItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = LayoutFoodItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val data = list[position]

        Glide.with(context).load(data.foodCoverImg).into(holder.binding.imageView)
        holder.binding.tvNama.text = data.foodName
        holder.binding.tvDes.text = data.foodDesc
        holder.binding.tvHarga.text = data.foodPrice

        holder.binding.btn.text = data.foodPrice
    }
}