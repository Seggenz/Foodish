package com.example.foodish

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodish.databinding.FragmentFoodItemBinding

//data class FoodItem(val name: String, val imageUrl: String)
class FoodListAdapter(private val foodList: List<FoodItem>) : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    class FoodViewHolder(val binding: FragmentFoodItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = FragmentFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = foodList[position]
        holder.binding.foodName.text = foodItem.name
        Glide.with(holder.binding.foodImage.context)
            .load(foodItem.imageUrl)
            .into(holder.binding.foodImage)

        updateFavoriteIcon(holder.binding.favouriteButton, foodItem.isFavorite)

        holder.binding.favouriteButton.setOnClickListener {
            foodItem.isFavorite = !foodItem.isFavorite
            updateFavoriteIcon(holder.binding.favouriteButton, foodItem.isFavorite)
        }
    }

    private fun updateFavoriteIcon(button: ImageButton, isFavorite: Boolean) {
        if(isFavorite) {
            button.setImageResource(R.drawable.star_filled)
        }
        else {
            button.setImageResource(R.drawable.star_outline)
        }
    }

    override fun getItemCount() = foodList.size
}
