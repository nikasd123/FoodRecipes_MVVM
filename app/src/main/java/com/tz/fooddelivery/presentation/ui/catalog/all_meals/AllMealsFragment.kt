package com.tz.fooddelivery.presentation.ui.catalog.all_meals

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tz.fooddelivery.R
import com.tz.fooddelivery.databinding.FragmentAllMealsBinding
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.presentation.common.setupLinearRecyclerView
import com.tz.fooddelivery.presentation.common.setupLinearRecyclerViewWithShimmer
import com.tz.fooddelivery.presentation.common.showRecyclerView
import com.tz.fooddelivery.presentation.ui.catalog.CategoriesState
import com.tz.fooddelivery.presentation.ui.catalog.DishesState
import com.tz.fooddelivery.presentation.ui.catalog.adapters.AllMealsAdapter
import com.tz.fooddelivery.presentation.ui.catalog.adapters.FiltersAdapter
import com.tz.fooddelivery.presentation.ui.catalog.adapters.ShimmerFiltersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllMealsFragment : Fragment(R.layout.fragment_all_meals) {

    private var _binding : FragmentAllMealsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AllMealsViewModel by viewModels()

    private val filtersAdapter by lazy { FiltersAdapter{ category -> viewModel.selectCategory(category)} }
    private val allMealsAdapter by lazy { AllMealsAdapter(::onMealItemCLick, ::onFavoriteClick) }
    private val filtersShimmerAdapter by lazy { ShimmerFiltersAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAllMealsBinding.bind(view)

        setupRecyclerViews()
        setupObservers()
    }

    private fun setupObservers(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch { initCategoriesState() }
                launch { initDishesState() }
                launch { initSelectedCategory() }
                launch { initSearchFieldListener() }
            }
        }
    }

    private fun initSearchFieldListener(){
        binding.SearchField.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateSearchQuery(query = s.toString())
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }

    private suspend fun initSelectedCategory(){
        viewModel.selectedCategory.collect{ category ->
            filtersAdapter.setSelectedCategory(category)
        }
    }

    private suspend fun initDishesState(){
        viewModel.dishesState.collect{ state ->
            when(state) {
                is DishesState.Loading -> {}
                is DishesState.Error -> {}
                is DishesState.Success -> {
                    Log.e("Image Test", state.dishes.toString())
                    allMealsAdapter.submitList(state.dishes)
                }
            }
        }
    }

    private suspend fun initCategoriesState(){
        viewModel.categoriesState.collect{ state ->
            when(state) {
                is CategoriesState.Loading -> showFiltersShimmer(isShow = true)
                is CategoriesState.Error -> {
                    showFiltersShimmer(false)
                    showError(state.message)
                }
                is CategoriesState.Success -> {
                    showFiltersShimmer(false)
                    filtersAdapter.submitList(state.categories)
                }
            }
        }
    }

    private fun setupRecyclerViews(){
        setupLinearRecyclerView(
            recyclerView = binding.allMealsCatalog,
            adapter = allMealsAdapter,
            orientation = LinearLayoutManager.VERTICAL
        )

        setupLinearRecyclerViewWithShimmer(
            recyclerView = binding.rvFilters,
            shimmerRecyclerView = binding.rvShimmerFilters,
            adapter = filtersAdapter,
            shimmerAdapter = filtersShimmerAdapter,
            orientation = LinearLayoutManager.HORIZONTAL
        )
    }

    private fun showFiltersShimmer(isShow: Boolean) {
        binding.rvShimmerFilters.showRecyclerView(isShow)
        binding.rvFilters.showRecyclerView(!isShow)
    }

    private fun onMealItemCLick(dishItem: DishItem){
        val action = AllMealsFragmentDirections.actionAllMealsFragmentToMealRecipeFragment(
            mealId = dishItem.id,
            title = dishItem.title,
            imageUrl = dishItem.image
        )
        findNavController().navigate(action)
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") { viewModel.retry() }
            .show()
    }

    private fun onFavoriteClick(dishItem: DishItem){
        viewModel.handleFavoriteButtonClick(dishItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}