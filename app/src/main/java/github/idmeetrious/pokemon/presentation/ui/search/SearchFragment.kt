package github.idmeetrious.pokemon.presentation.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import github.idmeetrious.pokemon.R
import github.idmeetrious.pokemon.databinding.FragmentSearchBinding
import github.idmeetrious.pokemon.domain.common.Status
import github.idmeetrious.pokemon.domain.entities.Pokemon
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

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateProgressBar()
        mainScope.launch {
            viewModel.pokemon.collect { pokemon ->
                pokemon?.let {
                    binding.searchResultTv.apply {
                        visibility = View.VISIBLE
                    }

                    binding.searchResultItemLayout.apply {
                        searchResultItemLayout.visibility = View.VISIBLE
                        it.sprites.let { sprite ->
                            loadImage(sprite.backDefault, itemBackDefIv)
                            loadImage(sprite.backShiny, itemBackShinyIv)
                            loadImage(sprite.frontDefault, itemFrontDefIv)
                            loadImage(sprite.frontShiny, itemFrontShinyIv)
                        }
                        loadItem(it, itemIdTv, itemNameTv, itemHeightTv, itemWeightTv)
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
        binding.searchResultItemLayout.itemFavoriteBtn.apply {
            setOnClickListener {
                Log.i(TAG, "--> onViewCreated: Add favorite clicked")
                viewModel.addFavorite()
            }
        }
        updateFavoriteButton()
    }

    private fun updateProgressBar() {
        binding.searchProgress.apply {
            mainScope.launch {
                viewModel.downloadState.collect { status ->
                    visibility = when (status) {
                        Status.LOADING -> { View.VISIBLE }
                        Status.SUCCESS -> { View.GONE }
                        Status.ERROR -> { View.GONE }
                    }
                }
            }
        }
    }

    private fun updateFavoriteButton() {
        binding.searchResultItemLayout.itemFavoriteBtn.apply {
            mainScope.launch {
                viewModel.uploadState.collect { status ->
                    if (status == Status.SUCCESS) {
                        setText(R.string.success_in_favorite_btn)
                    } else {
                        setText(R.string.add_to_favorite_btn)
                    }
                }
            }
        }
    }

    private fun loadImage(uri: String, iv: ImageView) {
        view?.let {
            iv.scaleX = 1.2f
            iv.scaleY = 1.2f
            Glide.with(it)
                .load(uri)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(iv)
        }
    }

    private fun loadItem(
        p: Pokemon, id: TextView, name: TextView, height: TextView, weight: TextView
    ) {
        id.text = "${p.id}"
        name.text = p.name
        height.text = "${p.height}"
        weight.text = "${p.weight}"
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