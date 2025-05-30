package com.tz.fooddelivery.presentation.ui.recipe

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.transition.MaterialContainerTransform
import com.tz.fooddelivery.R
import com.tz.fooddelivery.databinding.FragmentMealRecipeBinding
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation

@AndroidEntryPoint
class MealRecipeFragment : Fragment(R.layout.fragment_meal_recipe) {

    private var _binding: FragmentMealRecipeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MealRecipeViewModel by viewModels()
    private val args: MealRecipeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMealRecipeById(args.mealId)
        smoothTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMealRecipeBinding.bind(view)

        Log.d("AAA", "image: ${args.imageUrl}")

        setupUI()
    }

    private fun setupUI() {
        setupToolbar()
        with(binding) {
            textDishName.text = args.title

            Glide.with(imageMain)
                .load(args.imageUrl)
                .into(imageMain)

            Glide.with(imageBlurred)
                .load(args.imageUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(30, 3)))
                .into(imageBlurred)
        }
    }

    private fun smoothTransition() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300L
            scrimColor = Color.TRANSPARENT
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            duration = 300L
            scrimColor = Color.TRANSPARENT
        }
    }

    private fun setupToolbar(){
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}