package com.tz.fooddelivery.presentation.ui.catalog

import android.os.Bundle
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
import com.tz.fooddelivery.databinding.FragmentCatalogNewBinding
import com.tz.fooddelivery.domain.models.BannerItem
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.presentation.common.setupLinearRecyclerViewWithShimmer
import com.tz.fooddelivery.presentation.common.showRecyclerView
import com.tz.fooddelivery.presentation.ui.catalog.adapters.BannerAdapter
import com.tz.fooddelivery.presentation.ui.catalog.adapters.FiltersAdapter
import com.tz.fooddelivery.presentation.ui.catalog.adapters.MealsAdapter
import com.tz.fooddelivery.presentation.ui.catalog.adapters.ShimmerDishesAdapter
import com.tz.fooddelivery.presentation.ui.catalog.adapters.ShimmerFiltersAdapter
import com.tz.fooddelivery.presentation.utils.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog_new) {

    private var _binding: FragmentCatalogNewBinding? = null
    private val binding get() = _binding!!

    private val networkMonitor: NetworkMonitor by lazy { NetworkMonitor(requireContext()) }
    private val viewModel: CatalogViewModel by viewModels()
    private val mealsAdapter by lazy { MealsAdapter(::onMealItemClick, ::onFavoriteClick) }
    private val favoriteMealsAdapter by lazy {MealsAdapter(::onMealItemClick, ::onFavoriteClick)}
    private val bannersAdapter by lazy { BannerAdapter() }
    private val shimmerFiltersAdapter by lazy { ShimmerFiltersAdapter() }
    private val shimmerDishesAdapter by lazy { ShimmerDishesAdapter() }
    private val filtersAdapter = FiltersAdapter { category ->
        viewModel.selectCategory(category)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNetworkConnectionObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatalogNewBinding.bind(view)

        setupRecyclerViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { initDishesState() }
                launch { initCategoriesState() }
                launch { initSelectedCategory() }
                launch { initFavoriteDishesState() }
            }
        }
    }

    private suspend fun initCategoriesState() {
        viewModel.categoriesState.collect { state ->

            val test: List<Category> = listOf(Category(id = "0", category = "Для вас", originalName = "", isActive = true))

            when (state) {
                is CategoriesState.Loading -> showFiltersShimmer(true)
                is CategoriesState.Error -> {
                    showFiltersShimmer(false)
                    showError(state.message)
                }

                is CategoriesState.Success -> {
                    showFiltersShimmer(false)
                    filtersAdapter.submitList(test + state.categories)
                }
            }
        }
    }

    private suspend fun initFavoriteDishesState(){
        viewModel.favoriteDishesState.collect{ state ->
            when(state) {
                is FavoriteDishesState.Loading -> {Unit}
                is FavoriteDishesState.Error -> {
                    Unit
                }
                is FavoriteDishesState.Success -> {
                    favoriteMealsAdapter.submitList(state.favoriteDishes)
                }
            }
        }
    }

    private suspend fun initDishesState() {
        viewModel.dishesState.collect { state ->
            when (state) {
                is DishesState.Loading -> showDishesShimmer(isShow = true)
                is DishesState.Error -> {
                    showDishesShimmer(isShow = false)
                    showError(state.message)
                }

                is DishesState.Success -> {
                    showDishesShimmer(isShow = false)
                    mealsAdapter.submitList(state.dishes)
                }

            }
        }
    }

    private suspend fun initSelectedCategory() {
        viewModel.selectedCategory.collect { category ->
            filtersAdapter.setSelectedCategory(category)
        }
    }

    private fun showFiltersShimmer(isShow: Boolean) {
        binding.rvShimmerFilters.showRecyclerView(isShow)
        binding.rvFilters.showRecyclerView(!isShow)
    }

    private fun showDishesShimmer(isShow: Boolean) {
        binding.rvShimmerCatalog.showRecyclerView(isShow)
        binding.rvCatalog.showRecyclerView(!isShow)
    }

    private fun setupRecyclerViews() {
        setupLinearRecyclerViewWithShimmer(
            recyclerView = binding.rvFilters,
            shimmerRecyclerView = binding.rvShimmerFilters,
            adapter = filtersAdapter,
            shimmerAdapter = shimmerFiltersAdapter,
            orientation = LinearLayoutManager.HORIZONTAL
        )

        setupLinearRecyclerViewWithShimmer(
            recyclerView = binding.rvCatalog,
            shimmerRecyclerView = binding.rvShimmerCatalog,
            adapter = mealsAdapter,
            shimmerAdapter = shimmerDishesAdapter,
            orientation = LinearLayoutManager.HORIZONTAL
        )

        setupLinearRecyclerViewWithShimmer(
            recyclerView = binding.favCatalog,
            shimmerRecyclerView = binding.rvShimmerCatalog,
            adapter = favoriteMealsAdapter,
            shimmerAdapter = shimmerDishesAdapter,
            orientation = LinearLayoutManager.HORIZONTAL
        )

        bannersAdapter.submitList(getBannerItems())
    }

    private fun onMealItemClick(dishItem: DishItem){
        val action = CatalogFragmentDirections.actionCatalogFragmentToMealRecipeFragment(
            mealId = dishItem.id,
            title = dishItem.title,
            imageUrl = dishItem.image
        )
        findNavController().navigate(action)
    }

    private fun onFavoriteClick(dishItem: DishItem){
        Log.e("favorite button", "click")
        viewModel.handleFavoriteButtonClick(dishItem)
    }

    private fun initNetworkConnectionObserver() {
        networkMonitor.observe(this) { isConnected ->
            if (isConnected) viewModel.retry()
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") { viewModel.retry() }
            .show()
    }

    private fun getBannerItems() = listOf(
        BannerItem(R.drawable.banner),
        BannerItem(R.drawable.banner)
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}