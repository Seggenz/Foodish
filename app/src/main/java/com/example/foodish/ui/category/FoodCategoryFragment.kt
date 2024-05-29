package com.example.foodish.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.foodish.databinding.FragmentCategoryChooseBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.foodish.R
import com.example.foodish.data.database.FavoriteFoodDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FoodCategoryFragment : Fragment() {
    private var _binding: FragmentCategoryChooseBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        val amountSeekBar: SeekBar = binding.foodAmountSeekbar
        val selectedAmountTextView: TextView = binding.selectedAmount

        amountSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                selectedAmountTextView.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        binding.getFoodButton.setOnClickListener {
            val selectedOption = categorySpinner.selectedItem.toString()
            val selectedAmount = amountSeekBar.progress
            val action =
                FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment(
                    selectedOption,
                    selectedAmount
                )

            findNavController().navigate(action)
        }
        binding.seeDatabaseButton.setOnClickListener {
            checkDatabaseAndNavigate()
        }
    }

    private fun checkDatabaseAndNavigate() {
        lifecycleScope.launch {
            val database = FavoriteFoodDatabase.getDatabase(requireContext())
            val isEmpty = withContext(Dispatchers.IO) {
                database.foodDatabaseDao().getFavoriteFoodItems().isEmpty()
            }
            if (isEmpty) {
                Toast.makeText(context, "Database is empty", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_foodCategoryFragment_to_favoriteFoodFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}