package com.thrq.bitebox.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thrq.bitebox.activity.FoodDetailActivity
import com.thrq.bitebox.databinding.LayoutCartItemBinding
import com.thrq.bitebox.roomdb.AppDatabase
import com.thrq.bitebox.roomdb.FoodModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdapter( val context : Context, val  list :List<FoodModel>) :
RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    inner class CartViewHolder(val binding : LayoutCartItemBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = LayoutCartItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Glide.with(context).load(list[position].foodImages).into(holder.binding.ivFood)

        holder.binding.tvFood.text = list[position].foodName
        holder.binding.tvHarga.text = list[position].foodPrice

        holder.itemView.setOnClickListener{
            val intent = Intent(context, FoodDetailActivity::class.java)
            intent.putExtra("id", list[position].foodId)
            context.startActivity(intent)
        }

        val dao = AppDatabase.getInstance(context).foodDao()
        holder.binding.ivDel.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO){
                dao.deleteFood(FoodModel(list[position].foodId, list[position].foodName, list[position].foodImages, list[position].foodPrice))

            }
        }
    }
}