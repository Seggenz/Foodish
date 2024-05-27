package com.example.foodish

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodish.databinding.FragmentFoodItemBinding

class FoodListAdapter(private val foodList: List<FoodItem>) : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    class FoodViewHolder(val binding: FragmentFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val foodName: TextView = binding.foodName
        val foodImage: ImageView = binding.foodImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = FragmentFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = foodList[position]
        holder.foodName.text = foodItem.name
        holder.foodImage.setImageResource(foodItem.imageResId)
    }

    override fun getItemCount() = foodList.size
}
