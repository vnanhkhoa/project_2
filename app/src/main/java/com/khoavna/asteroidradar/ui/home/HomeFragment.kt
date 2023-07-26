package com.khoavna.asteroidradar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.khoavna.asteroidradar.R
import com.khoavna.asteroidradar.data.network.wapi.apod.Apod
import com.khoavna.asteroidradar.databinding.FragmentHomeBinding
import com.khoavna.asteroidradar.ui.home.adapter.AsteroidAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.FACTORY
    }
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val asteroidAdapter = AsteroidAdapter {
        HomeFragmentDirections.actionHomeFragmentToDetailFragment(it).also { action ->
            findNavController().navigate(action)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchImageOfDay()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rcAsteroid.also {
                it.adapter = asteroidAdapter
                it.layoutManager = LinearLayoutManager(requireActivity())
            }
        }

        initObserver()
    }

    private fun initObserver() {
        viewModel.apply {
            asteroids.observe(viewLifecycleOwner) { asteroids ->
                val list = listOf(AsteroidAdapter.Item.HEADER(imageResult.value ?: Apod()))
                asteroids.map { AsteroidAdapter.Item.NORMAL(it) }.also {
                    asteroidAdapter.submitList(list + it)
                }
            }

            imageResult.observe(viewLifecycleOwner) { apod ->
                val header = listOf(AsteroidAdapter.Item.HEADER(apod))
                val normal = asteroids.value?. map { AsteroidAdapter.Item.NORMAL(it) } ?: emptyList()
                asteroidAdapter.submitList(header + normal)
            }
        }

    }
}