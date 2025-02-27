package com.tz.fooddelivery.presentation.catalog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tz.fooddelivery.R
import com.tz.fooddelivery.databinding.FragmentCatalogBinding
import com.tz.fooddelivery.domain.models.BannerItem
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.presentation.catalog.adapters.BannerAdapter
import com.tz.fooddelivery.presentation.catalog.adapters.FiltersAdapter
import com.tz.fooddelivery.presentation.catalog.adapters.MealsAdapter
import com.tz.fooddelivery.presentation.utils.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val networkMonitor: NetworkMonitor by lazy { NetworkMonitor(requireContext()) }
    private val viewModel: CatalogViewModel by viewModels()
    private val filtersAdapter = FiltersAdapter(::onCategorySelected)
    private val mealsAdapter by lazy { MealsAdapter() }
    private val bannersAdapter by lazy { BannerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNetworkConnectionObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatalogBinding.bind(view)

        setupRecyclerViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                initDishesObserver()
                initCategoriesObserver()
            }
        }
    }

    private suspend fun initDishesObserver() {
        viewModel.uiState.collect { state ->
            when (state) {
                is CatalogViewModel.MealsUiState.Loading -> showLoading(true)
                is CatalogViewModel.MealsUiState.Success -> {
                    showLoading(false)
                    mealsAdapter.submitList(state.dishes)
                }

                is CatalogViewModel.MealsUiState.Error -> {
                    showLoading(false)
                    showError(state.message)
                }
            }
        }
    }

    private suspend fun initCategoriesObserver() {
        viewModel.categoriesState.collect { state ->
            when (state) {
                is CatalogViewModel.CategoriesUiState.Loading -> showLoading(true)
                is CatalogViewModel.CategoriesUiState.Success -> {
                    showLoading(false)
                    filtersAdapter.submitList(state.categories)
                }

                is CatalogViewModel.CategoriesUiState.Error -> {
                    showLoading(false)
                    showCategoriesError(state.message)
                }
            }
        }
    }

    private fun setupRecyclerViews() {
        with(binding) {
            rvFilters.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = filtersAdapter
            }

            rvCatalog.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = mealsAdapter
            }

            rvBanners.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = bannersAdapter
            }
        }

        bannersAdapter.submitList(getBannerItems())
    }

    private fun initNetworkConnectionObserver() {
        networkMonitor.observe(this) { isConnected ->
            if (isConnected) {
                viewModel.retry()
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("Retry") { viewModel.retry() }
            .show()
    }

    private fun showCategoriesError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun onCategorySelected(category: Category) {
        viewModel.selectCategory(category.category)
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