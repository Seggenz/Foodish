package com.example.foodish.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodish.R
import com.example.foodish.data.api.FoodishResponse
import com.example.foodish.data.api.RetrofitInstance
import com.example.foodish.data.database.FavoriteFoodDatabase
import com.example.foodish.data.model.FoodItem
import com.example.foodish.databinding.FragmentFoodListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class FoodListFragment : Fragment() {
    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!

    private val args: FoodListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedCategory = args.selectedCategory
        val selectedAmount = args.selectedAmount
        fetchFoodImages(selectedCategory, selectedAmount)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_foodListFragment_to_foodCategoryFragment)
        }
    }

    private fun fetchFoodImages(category: String, count: Int) {
        val foodList = mutableListOf<FoodItem>()

        for (i in 1..count) {
            RetrofitInstance.api.getImage(category).enqueue(object : Callback<FoodishResponse> {
                override fun onResponse(call: Call<FoodishResponse>, response: Response<FoodishResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        foodList.add(FoodItem(name = category.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        }, imageUrl = response.body()!!.image))
                        if (foodList.size == count) {
                            setupRecyclerView(foodList)
                        }
                    } else {
                        showError()
                    }
                }

                override fun onFailure(call: Call<FoodishResponse>, t: Throwable) {
                    showError()
                }
            })
        }
    }

    private fun setupRecyclerView(foodList: List<FoodItem>) {
        val adapter = FoodListAdapter(requireContext(), foodList) { foodItem, isFavorite ->
            CoroutineScope(Dispatchers.IO).launch {
                val database = FavoriteFoodDatabase.getDatabase(requireContext())
                if (isFavorite) {
                    database.foodDatabaseDao().insertFood(foodItem)
                } else {
                    database.foodDatabaseDao().deleteFood(foodItem)

                }
            }
        }
        binding.foodList.layoutManager = LinearLayoutManager(context)
        binding.foodList.adapter = adapter
    }


    private fun showError() {
        Toast.makeText(context, "Failed to load images", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
