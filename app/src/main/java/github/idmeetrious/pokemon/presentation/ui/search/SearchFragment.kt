package github.idmeetrious.pokemon.presentation.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import github.idmeetrious.pokemon.databinding.FragmentSearchBinding
import github.idmeetrious.pokemon.domain.common.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

private const val TAG = "SearchFragment"

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())
    private val mainScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val rootView = binding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainScope.launch {
            viewModel.status.collect { status ->
                when (status) {
                    Status.LOADING -> {
                        Log.i(TAG, "--> onViewCreated: LOADING")
                        binding.searchProgress.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        Log.i(TAG, "--> onViewCreated: SUCCESS")
                        binding.searchProgress.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        Log.i(TAG, "--> onViewCreated: ERROR")
                        binding.searchProgress.visibility = View.GONE
                    }
                }
            }
        }
        mainScope.launch {
            viewModel.pokemon.collect { pokemon ->
                pokemon?.let {
                    binding.searchResultTv.apply {
                        visibility = View.VISIBLE
                        append("1")
                    }

                    binding.searchResultItemLayout.apply {
                        searchResultItemLayout.visibility = View.VISIBLE
                        Glide.with(view)
                            .load(it.sprites.frontDefault)
                            .into(itemFrontDefIv)
                    }
                }
            }
        }

        binding.searchFieldBtn.setOnClickListener {
            Log.i(TAG, "--> onViewCreated: Search clicked")
            val query = binding.searchFieldEt.text.toString()
            if (query.trim().isNotEmpty())
                ioScope.launch {
                    viewModel.findPokemon(query)
                }
            hideKeyboard()
        }
        binding.searchResultItemLayout.itemFavoriteBtn.setOnClickListener {
            Log.i(TAG, "--> onViewCreated: Add favorite clicked")
        }
    }

    private fun hideKeyboard() {
        val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
        ioScope.cancel()
    }
}