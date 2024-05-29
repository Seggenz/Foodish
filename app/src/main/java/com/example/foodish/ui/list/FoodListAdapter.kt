package com.example.foodish.ui.list

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodish.R
import com.example.foodish.data.model.FoodItem
import com.example.foodish.databinding.FragmentFoodItemBinding

class FoodListAdapter(
    private val context: Context,
    private val foodList: List<FoodItem>,
    private val onFavoriteClick: (FoodItem, Boolean) -> Unit
) : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

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
            onFavoriteClick(foodItem, foodItem.isFavorite)
        }

        holder.binding.foodImage.setOnLongClickListener {
            showImageDialog(foodItem.imageUrl)
            true
        }
    }

    private fun updateFavoriteIcon(button: ImageButton, isFavorite: Boolean) {
        if (isFavorite) {
            button.setImageResource(R.drawable.star_filled)
        } else {
            button.setImageResource(R.drawable.star_outline)
        }
    }

    private fun showImageDialog(imageUrl: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_image, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.expanded_image)
        Glide.with(context).load(imageUrl).into(imageView)

        AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok, null)
            .create()
            .show()
    }

    override fun getItemCount() = foodList.size
}
