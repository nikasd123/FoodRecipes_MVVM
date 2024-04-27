package com.tz.fooddelivery.presentation.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tz.fooddelivery.R
import com.tz.fooddelivery.databinding.FragmentCatalogBinding
import com.tz.fooddelivery.domain.models.BannerItem
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.presentation.MainActivity
import com.tz.fooddelivery.presentation.catalog.adapters.BannerAdapter
import com.tz.fooddelivery.presentation.catalog.adapters.MealsAdapter
import com.tz.fooddelivery.presentation.catalog.adapters.FiltersAdapter
import dagger.hilt.android.AndroidEntryPoint

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
        (activity as MainActivity).showProgressBar()

        initFun()
    }

    private fun initFun(){
        initRecyclerViews()
        setupObservers()
        initRecyclerItemList()
    }

    private fun setupObservers(){
        viewModel.dishesList.observe(viewLifecycleOwner){ dishes ->
            mealsAdapter.submitList(dishes)
            (activity as MainActivity).hideProgressBar()
        }

        viewModel.categoriesList.observe(viewLifecycleOwner){ categories ->
            filtersAdapter.submitList(categories)
        }
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
        viewModel.getMealsByCategory(category.category)
        (activity as MainActivity).showProgressBar()
    }

    private fun getBannerItems(): List<BannerItem> =
        listOf(
            BannerItem(R.drawable.banner),
            BannerItem(R.drawable.banner)
        )
}