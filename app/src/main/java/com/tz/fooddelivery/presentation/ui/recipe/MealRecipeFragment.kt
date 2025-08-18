package com.tz.fooddelivery.presentation.ui.recipe

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.tz.fooddelivery.R
import com.tz.fooddelivery.databinding.FragmentMealRecipeBinding
import com.tz.fooddelivery.domain.models.IngredientItem
import com.tz.fooddelivery.domain.models.MealRecipe
import com.tz.fooddelivery.presentation.ui.recipe.adapters.IngredientsAdapter
import com.tz.fooddelivery.presentation.ui.recipe.adapters.ShortInfoAdapter
import com.tz.fooddelivery.presentation.ui.recipe.adapters.ShortInfoItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealRecipeFragment : Fragment(R.layout.fragment_meal_recipe) {

    private var _binding: FragmentMealRecipeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MealRecipeViewModel by viewModels()
    private val args: MealRecipeFragmentArgs by navArgs()
    private var showAllIngredients = false

    private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    private val shortInfoAdapter: ShortInfoAdapter by lazy { ShortInfoAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.saveMealId(args.mealId).also {
            viewModel.getMealRecipeById(args.mealId)
        }
        smoothTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMealRecipeBinding.bind(view)
        setupUI()
    }

    private fun setupUI() { with(binding) {
        setupButtons()
        setupRecyclerViews()
        setupObservers()
        Glide.with(imageMain)
            .load(args.imageUrl)
            .into(imageMain)
    }}

    private fun setupObservers(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.recipe.collect{ state ->
                        when (state){
                            is RecipeState.Error -> showError(state.message)
                            RecipeState.Loading -> Unit
                            is RecipeState.Success -> initRecipeInfo(state.recipes)
                        }
                    }
                }
                launch {
                    viewModel.event.collect { event ->
                        when (event) {
                            is MealRecipeViewModel.Event.OpenYoutube ->
                                openYoutubeLink(event.url)
                        }
                    }
                }
            }
        }
    }

    private fun initRecipeInfo(recipe: MealRecipe){
        setupShortInfo(recipe.mealCategory, "25", recipe.area)
        setupYoutubeButton(recipe.strYoutube)
        binding.apply {
            updateIngredients(recipe.ingredientsList)
            recipeDescription.text = recipe.mealRecipe
        }
    }

    private fun setupYoutubeButton(youtubeUrl: String) {
        binding.youtubeBtn.apply {
            visibility = if(youtubeUrl.isNotBlank()) View.VISIBLE else View.GONE
            setOnClickListener { viewModel.openYoutube(youtubeUrl) }
        }
    }

    private fun openYoutubeLink(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = url.toUri()
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            ContextCompat.startActivity(requireContext(), intent, null)
        } catch (e: Exception) {
            Snackbar.make(binding.root, "YouTube app not installed", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerViews() {
        binding.ingredientsRv.apply {
            adapter = ingredientsAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
        binding.shortInfoRv.apply {
            adapter = shortInfoAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun updateIngredients(ingredients: List<IngredientItem>) {
        ingredientsAdapter.submitList(ingredients)
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

    private fun setupButtons(){
        binding.apply {
            navigateBack.setOnClickListener { findNavController().navigateUp() }
            likeBtn.setOnClickListener {  }
            seeAllIngredients.setOnClickListener {
                showAllIngredients = !showAllIngredients //todo viewModel
                ingredientsAdapter.toggleShowAll(showAllIngredients)

                val buttonText = if (showAllIngredients) "Свернуть" else "Все"
                seeAllIngredients.text = buttonText
            }
        }
    }

    private fun setupShortInfo(category: String, time: String, area: String){
        binding.textDishName.text = args.title
        val infoItems = listOf(
            ShortInfoItem(ShortInfoItem.InfoType.CATEGORY, category),
            ShortInfoItem(ShortInfoItem.InfoType.TIME, "$time мин"),
            ShortInfoItem(ShortInfoItem.InfoType.AREA, area)
        )

        shortInfoAdapter.submitList(infoItems)
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") { viewModel.retry() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}