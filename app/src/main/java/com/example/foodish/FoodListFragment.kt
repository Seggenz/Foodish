package com.example.foodish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.navArgs
import com.example.foodish.databinding.FragmentFoodListBinding

class FoodListFragment : Fragment() {
    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!

//    private val args: FoodListFragmentArgs by navArgs() // Upewnij się, że ten wiersz jest poprawnie używany

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val selectedCategory = args.selectedCategory

        // Use selectedCategory to filter your food list or fetch data accordingly
        val foodList = listOf(
            FoodItem("Pizza", R.drawable.pizza),
            FoodItem("Burger", R.drawable.pizza),
            FoodItem("Sushi", R.drawable.pizza)
            // Add more items as needed
        )

        val adapter = FoodListAdapter(foodList)
        binding.foodList.layoutManager = LinearLayoutManager(context)
        binding.foodList.adapter = adapter

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_foodListFragment_to_foodCategoryFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
