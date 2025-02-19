package com.tz.fooddelivery.presentation.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tz.fooddelivery.R
import com.tz.fooddelivery.databinding.FragmentCatalogBinding
import com.tz.fooddelivery.domain.common.State
import com.tz.fooddelivery.domain.models.BannerItem
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.presentation.MainActivity
import com.tz.fooddelivery.presentation.catalog.adapters.BannerAdapter
import com.tz.fooddelivery.presentation.catalog.adapters.MealsAdapter
import com.tz.fooddelivery.presentation.catalog.adapters.FiltersAdapter
import com.tz.fooddelivery.presentation.common.gone
import com.tz.fooddelivery.presentation.common.setViewsVisibility
import com.tz.fooddelivery.presentation.common.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private lateinit var binding: FragmentCatalogBinding
    private val filtersAdapter: FiltersAdapter by lazy { FiltersAdapter(::onItemClick) }
    private val mealsAdapter: MealsAdapter by lazy { MealsAdapter() }
    private val bannersAdapter: BannerAdapter by lazy { BannerAdapter() }
    private val viewModel: CatalogViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCatalogBinding.bind(view)

        initFun()
    }

    private fun initFun(){
        setupObservers()
        initRecyclerViews()
        initRecyclerItemList()
    }

    private fun setupObservers() {
        viewModel.state.onEach { state ->
            when (state) {
                is State.Loading -> {
                    setViewsVisibility(
                        binding.progressBar to true,
                        binding.rvFilters to false,
                        binding.rvCatalog to false
                    )
                }
                is State.Success -> {
                    val (dishes, categories) = state.data
                    updateAdapters(meals = dishes, categories =  categories)
                    setViewsVisibility(
                        binding.progressBar to false,
                        binding.rvFilters to true,
                        binding.rvCatalog to true
                    )
                }
                is State.Error -> {
                    showError(state.message)
                    setViewsVisibility(
                        binding.progressBar to false,
                        binding.rvFilters to false,
                        binding.rvCatalog to false
                    )
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun updateAdapters(meals: List<DishItem>, categories: List<Category>) {
        mealsAdapter.submitList(meals)
        filtersAdapter.submitList(categories)
    }

    private fun showError(message: String){

    }

    private fun initRecyclerViews(){
        binding.rvFilters.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = filtersAdapter
        }

        binding.rvCatalog.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mealsAdapter
        }

        binding.rvBanners.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = bannersAdapter
        }
    }

    private fun initRecyclerItemList(){
        bannersAdapter.submitList(getBannerItems())
    }

    private fun onItemClick(category: Category){
        viewModel.getDishesByCategory(category.category)
    }

    private fun getBannerItems(): List<BannerItem> =
        listOf(
            BannerItem(R.drawable.banner),
            BannerItem(R.drawable.banner)
        )
}