package com.tz.fooddelivery.presentation.ui.catalog.all_meals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tz.fooddelivery.R
import com.tz.fooddelivery.databinding.FragmentAllMealsBinding
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.presentation.common.setupLinearRecyclerView
import com.tz.fooddelivery.presentation.common.setupLinearRecyclerViewWithShimmer
import com.tz.fooddelivery.presentation.ui.catalog.DishesState
import com.tz.fooddelivery.presentation.ui.catalog.adapters.AllMealsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllMealsFragment : Fragment(R.layout.fragment_all_meals) {

    private var _binding : FragmentAllMealsBinding? = null
    private val binding get() = _binding!!

    private val allMealsAdapter by lazy { AllMealsAdapter(::onMealItemCLick, ::onFavoriteClick) }
    private val viewModel: AllMealsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAllMealsBinding.bind(view)

        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        setupLinearRecyclerView(
            recyclerView = binding.allMealsCatalog,
            adapter = allMealsAdapter,
            orientation = LinearLayoutManager.VERTICAL
        )
    }

    private fun onMealItemCLick(dishItem: DishItem){
        val action = AllMealsFragmentDirections.actionAllMealsFragmentToMealRecipeFragment(
            mealId = dishItem.id,
            title = dishItem.title,
            imageUrl = dishItem.image
        )
        findNavController().navigate(action)
    }

    private fun onFavoriteClick(dishItem: DishItem){
        viewModel.handleFavoriteButtonClick(dishItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}