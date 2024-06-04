package com.example.foodish.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodish.R
import com.example.foodish.data.database.FavoriteFoodDatabase
import com.example.foodish.data.model.FoodItem
import com.example.foodish.databinding.FragmentFavoriteFoodBinding
import com.example.foodish.ui.list.FoodListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteFoodFragment : Fragment() {
    private var _binding: FragmentFavoriteFoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchFavoriteFoods()
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteFoodFragment_to_foodCategoryFragment)
        }
    }

    private fun fetchFavoriteFoods() {
        CoroutineScope(Dispatchers.IO).launch {
            val database = FavoriteFoodDatabase.getDatabase(requireContext())
            val favoriteFoods = database.foodDatabaseDao().getFavoriteFoodItems()
            withContext(Dispatchers.Main) {
                if (favoriteFoods.isNotEmpty()) {
                    setupRecyclerView(favoriteFoods)
                } else {
                    Toast.makeText(context, "No favorite foods found", Toast.LENGTH_SHORT).show()
                    setupRecyclerView(emptyList())
                    findNavController().navigate(R.id.action_favoriteFoodFragment_to_foodCategoryFragment)
                }
            }
        }
    }

    private fun setupRecyclerView(foodList: List<FoodItem>) {
        val adapter = FoodListAdapter(requireContext(),foodList) { foodItem, isFavorite ->
            CoroutineScope(Dispatchers.IO).launch {
                val database = FavoriteFoodDatabase.getDatabase(requireContext())
                if (isFavorite) {
                    database.foodDatabaseDao().insertFood(foodItem)
                } else {
                    database.foodDatabaseDao().deleteFood(foodItem)
                }
                withContext(Dispatchers.Main) {
                    fetchFavoriteFoods()
                }
            }
        }
        binding.foodList.layoutManager = LinearLayoutManager(context)
        binding.foodList.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
