package com.khoavna.asteroidradar.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.khoavna.asteroidradar.databinding.FragmentDetailBinding
import com.khoavna.asteroidradar.ui.detail.adapter.DetailAdapter

class DetailFragment : Fragment() {

    private val binding by lazy {
        FragmentDetailBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<DetailViewModel>()
    private val detailAdapter = DetailAdapter {
        MaterialAlertDialogBuilder(requireContext(), androidx.appcompat.R.style.AlertDialog_AppCompat_Light)
            .setMessage(MESSAGE)
            .setPositiveButton("Accept") { _, _ ->
                // Don't something
            }
            .show()
    }
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcAsteroid.also {
            it.adapter = detailAdapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.updateListItem(args.asteroid)

        viewModel.items.observe(viewLifecycleOwner) {
            detailAdapter.submitList(it)
        }
    }

    companion object {
        const val MESSAGE = "The astronomical unit (au) is a unit of length, " +
                "roughly the distance from Earth to the Sun and equal to about " +
                "150 million kilometres (93 million miles)"
    }
}