package github.idmeetrious.pokemon.presentation.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.idmeetrious.pokemon.R
import github.idmeetrious.pokemon.databinding.FragmentFavoriteBinding
import github.idmeetrious.pokemon.domain.common.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {
    private val viewModel: FavoriteViewModel by lazy {
        ViewModelProvider(this).get(FavoriteViewModel::class.java)
    }
    private val ioScope = CoroutineScope(Dispatchers.IO + Job())
    private val mainScope = CoroutineScope(Dispatchers.Main + Job())

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private var rv: RecyclerView? = null
    private var adapter: FavoriteAdapter? = null
    private var progressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FavoriteAdapter(emptyList())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val rootView = binding.root
        rv = binding.favoriteRv
        progressBar = requireActivity().findViewById(R.id.main_search_progress)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv?.layoutManager = LinearLayoutManager(requireContext())
        rv?.adapter = adapter

        observeFavoriteList()
        observeDeletedItems()
        observeLoadingState()
        observeQueryState()
    }

    private fun observeLoadingState() {
        mainScope.launch {
            viewModel.downloadState.collect { status ->
                when (status) {
                    Status.LOADING -> {
                        progressBar?.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        progressBar?.visibility = View.INVISIBLE
                    }
                    Status.ERROR -> {
                        progressBar?.visibility = View.GONE
                        Toast.makeText(
                            requireContext(), getString(R.string.loading_error_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Status.INIT -> {
                        progressBar?.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun observeQueryState() {
        mainScope.launch {
            viewModel.queryState.collect { status ->
                when (status) {
                    Status.LOADING -> {
                        progressBar?.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        progressBar?.visibility = View.INVISIBLE
                        viewModel.loadFavorites()
                    }
                    Status.ERROR -> {
                        progressBar?.visibility = View.GONE
                        Toast.makeText(
                            requireContext(), getString(R.string.delete_error_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Status.INIT -> {
                        progressBar?.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun observeDeletedItems() {
        ioScope.launch {
            adapter?.pokemon?.collect {
                it?.let {
                    viewModel.deleteFavorite(it)
                }
            }
        }
    }

    private fun observeFavoriteList() {
        mainScope.launch {
            viewModel.favorites.collect {
                adapter?.updateList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}