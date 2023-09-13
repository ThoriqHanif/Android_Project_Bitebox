package com.thrq.bitebox.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.thrq.bitebox.activity.AddressActivity
import com.thrq.bitebox.adapter.CartAdapter
import com.thrq.bitebox.databinding.FragmentCartBinding
import com.thrq.bitebox.roomdb.AppDatabase
import com.thrq.bitebox.roomdb.FoodModel

class CartFragment : Fragment() {

    private lateinit var binding : FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCartBinding.inflate(layoutInflater)
        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val  editor = preference.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).foodDao()

        dao.getAllFood().observe(requireActivity()){
            binding.cartRecycle.adapter = CartAdapter(requireContext(), it)

            jumlahTotal(it)
        }

        return binding.root
    }

    private fun jumlahTotal(data: List<FoodModel>?) {
        var jumlah = 0
        for (item in data!!){
            jumlah += item.foodPrice!!.toInt()
        }

        binding.jumlahItem.text = "Total item ${data.size}"
        binding.jumlahTotal.text = "Total : Rp. $jumlah"

        binding.btnCheckout.setOnClickListener {
            val intent = Intent(context, AddressActivity::class.java)
            intent.putExtra("jumlahTotal", jumlah)
            startActivity(intent)
        }
    }


}