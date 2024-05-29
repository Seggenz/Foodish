package com.example.foodish

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.foodish.databinding.FragmentCategoryChooseBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.findNavController

class FoodCategoryFragment: Fragment() {
    private var _binding: FragmentCategoryChooseBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryChooseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categorySpinner: Spinner = binding.foodCategorySpinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.food_options_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
        }

        val amountSpinner: Spinner = binding.foodAmountSpinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.food_amount_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            amountSpinner.adapter = adapter
        }


        binding.getFoodButton.setOnClickListener {
            val selectedOption = categorySpinner.selectedItem.toString()
            val selectedAmount = amountSpinner.selectedItem.toString().toInt()
            val action =
                FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment(
                    selectedOption,
                    selectedAmount
                )
            findNavController().navigate(action)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}