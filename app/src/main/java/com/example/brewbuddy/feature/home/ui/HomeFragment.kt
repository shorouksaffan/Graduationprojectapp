package com.example.brewbuddy.feature.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brewbuddy.R
import com.example.brewbuddy.core.data.remote.Result
import com.example.brewbuddy.core.model.Drink
import com.example.brewbuddy.databinding.FragmentHomeBinding
import com.example.brewbuddy.feature.home.HomeViewModel
import com.example.brewbuddy.feature.home.ui.adapter.RecommendationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: RecommendationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvGreeting.text = getString(R.string.good_day_emad_rabie)

        // Setup RecyclerView
        adapter = RecommendationAdapter(mutableListOf())
        binding.rvRecommendations.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecommendations.adapter = adapter

        // Observe drinks from ViewModel
        collectDrinks()

        // Example clicks
        binding.iconGroup.setOnClickListener {
            Toast.makeText(requireContext(), "Menu clicked", Toast.LENGTH_SHORT).show()
        }

        binding.icBell.setOnClickListener {
            Toast.makeText(requireContext(), "Notifications clicked", Toast.LENGTH_SHORT).show()
        }

        binding.cvBestSeller.setOnClickListener {
            Toast.makeText(requireContext(), "Best seller clicked", Toast.LENGTH_SHORT).show()
        }

        binding.cvNewMenu.setOnClickListener {
            Toast.makeText(requireContext(), "New menu clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun collectDrinks() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allDrinks.collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        val drinks: List<Drink> = result.data
                        adapter.updateData(drinks)
                    }
                    is Result.Failure -> {
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Result.Loading -> {
                        // could show shimmer/progress bar instead
                        Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
