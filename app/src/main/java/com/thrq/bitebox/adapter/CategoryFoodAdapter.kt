package com.thrq.bitebox.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thrq.bitebox.activity.FoodDetailActivity
import com.thrq.bitebox.databinding.CategoryFoodLayoutBinding
import com.thrq.bitebox.databinding.LayoutFoodItemBinding
import com.thrq.bitebox.model.AddFoodModel

class CategoryFoodAdapter (val context: Context, val list : ArrayList<AddFoodModel>)
    :RecyclerView.Adapter<CategoryFoodAdapter.CategoryFoodViewHolder>(){

    inner class CategoryFoodViewHolder(val binding : CategoryFoodLayoutBinding)
        : RecyclerView.ViewHolder(binding.root){

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryFoodViewHolder {
        val binding = CategoryFoodLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return CategoryFoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryFoodViewHolder, position: Int) {
        Glide.with(context).load(list[position].foodCoverImg).into(holder.binding.imageView2)

        holder.binding.textView.text = list[position].foodName
        holder.binding.textView2.text = list[position].foodPrice

        holder.itemView.setOnClickListener{
            val intent = Intent(context, FoodDetailActivity::class.java)
            intent.putExtra("id", list[position].foodId)
            context.startActivity(intent)
        }
    }
}