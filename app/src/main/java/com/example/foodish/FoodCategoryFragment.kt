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
import com.example.foodish.databinding.ActivityMainBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FoodCategoryFragment: Fragment() {
    private var _binding: FragmentCategoryChooseBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryChooseBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = binding.foodCategorySpinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.food_options_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.getFoodButton.setOnClickListener {
            val selectedOption = spinner.selectedItem.toString()
            val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment(selectedOption)
            findNavController().navigate(action)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}