package com.yousef.seera.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import com.yousef.seera.adapter.LoadMoreAdapter
import com.yousef.seera.adapter.MoviesAdapter
import com.yousef.seera.adapter.RevenueAdaptar
import com.yousef.seera.adapter.TopRatedAdapter
import com.yousef.seera.databinding.FragmentMoviesBinding
import com.yousef.seera.utils.showAlertDialog
import com.yousef.seera.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    @Inject
    lateinit var  topRatedAdapter: TopRatedAdapter

    @Inject
    lateinit var revenueAdaptar: RevenueAdaptar

    private val viewModel: MoviesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {


            viewModel.errorMessage.observe(viewLifecycleOwner){
                    if(it.isNotEmpty())
                        binding.root.showAlertDialog("Serever Error",it,requireContext())
                }

            lifecycleScope.launchWhenCreated {
                viewModel.moviesList.collect {
                    moviesAdapter.submitData(it)
                }
            }
            lifecycleScope.launchWhenCreated {
                viewModel.listTopRated.collect{
                    topRatedAdapter.submitData(it)
                }
            }

            lifecycleScope.launchWhenCreated {
                viewModel.revenueList.collect{
                    revenueAdaptar.submitData(it)
                }
            }
            revenueAdaptar.setOnItemClickListener {
                val direction = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it.id)
                findNavController().navigate(direction)
            }

            lifecycleScope.launchWhenCreated {
                revenueAdaptar.loadStateFlow.collect{
                    val state = it.refresh
                    prgBarMovies.isVisible = state is LoadState.Loading
                }
            }

            moviesAdapter.setOnItemClickListener {
                val direction = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it.id)
                findNavController().navigate(direction)
            }

            lifecycleScope.launchWhenCreated {
                moviesAdapter.loadStateFlow.collect{
                    val state = it.refresh
                    prgBarMovies.isVisible = state is LoadState.Loading
                }
            }

            topRatedAdapter.setOnItemClickListener {
                val direction = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it.id)
                findNavController().navigate(direction)
            }

            lifecycleScope.launchWhenCreated {
                topRatedAdapter.loadStateFlow.collect{
                    val state = it.refresh
                    prgBarMovies.isVisible = state is LoadState.Loading
                }
            }


            rlMovies.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = moviesAdapter
            }

            rltopRated.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = topRatedAdapter
            }

            rlRevenue.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = revenueAdaptar
            }

            rlRevenue.adapter=revenueAdaptar.withLoadStateFooter(
                LoadMoreAdapter{
                    revenueAdaptar.retry()
                }
            )


            rlMovies.adapter=moviesAdapter.withLoadStateFooter(
                LoadMoreAdapter{
                    moviesAdapter.retry()
                }
            )
            rltopRated.adapter=topRatedAdapter.withLoadStateFooter(
                LoadMoreAdapter{
                    topRatedAdapter.retry()
                }
            )


        }
    }

}