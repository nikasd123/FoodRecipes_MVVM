package com.tz.fooddelivery.presentation.ui.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tz.fooddelivery.R
import com.tz.fooddelivery.databinding.FragmentCatalogBinding
import com.tz.fooddelivery.domain.models.BannerItem
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.presentation.ui.catalog.adapters.BannerAdapter
import com.tz.fooddelivery.presentation.ui.catalog.adapters.FiltersAdapter
import com.tz.fooddelivery.presentation.ui.catalog.adapters.MealsAdapter
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
                initObserver()
                initSelectedCategory()
            }
        }
    }

    private suspend fun initObserver() {
        viewModel.uiState.collect { state ->
            when (state) {
                is CatalogViewModel.CatalogUiState.Loading -> showLoading(isShow = true)
                is CatalogViewModel.CatalogUiState.Success -> {
                    showLoading(isShow = false)
                    mealsAdapter.submitList(state.dishes)
                    filtersAdapter.submitList(state.categories)
                }
                is CatalogViewModel.CatalogUiState.Error -> {
                    showLoading(isShow = false)
                    showError(state.message)
                }
            }
        }
    }

    private suspend fun initSelectedCategory(){
        viewModel.selectedCategory.collect{ category ->
            filtersAdapter.setSelectedCategory(category)
        }
    }

    private fun setupRecyclerViews() {
        with(binding) {
            setupRecyclerView(rvFilters, LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false), filtersAdapter)
            setupRecyclerView(rvCatalog, LinearLayoutManager(context), mealsAdapter)
            setupRecyclerView(rvBanners, LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false), bannersAdapter)
        }

        bannersAdapter.submitList(getBannerItems())
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        layoutManager: RecyclerView.LayoutManager,
        adapter: RecyclerView.Adapter<*>
    ) {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun initNetworkConnectionObserver() {
        networkMonitor.observe(this) { isConnected ->
            if (isConnected) {
                viewModel.retry()
            }
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("Retry") { viewModel.retry() }
            .show()
    }

    private fun onCategorySelected(category: Category) {
        viewModel.selectCategory(category)
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