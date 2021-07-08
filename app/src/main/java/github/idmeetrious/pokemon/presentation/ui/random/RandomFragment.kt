package github.idmeetrious.pokemon.presentation.ui.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import github.idmeetrious.pokemon.R
import github.idmeetrious.pokemon.databinding.FragmentRandomBinding
import github.idmeetrious.pokemon.domain.common.Status
import github.idmeetrious.pokemon.domain.entities.Pokemon
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

private const val TAG = "RandomFragment"

class RandomFragment : Fragment() {

    private val viewModel: RandomViewModel by lazy {
        ViewModelProvider(this).get(RandomViewModel::class.java)
    }

    private val mainScope = CoroutineScope(Dispatchers.Main + Job())
    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    private var _binding: FragmentRandomBinding? = null
    private val binding get() = _binding!!

    private var progressBar: ProgressBar? = null
    private var itemView: ConstraintLayout? = null
    private var favoriteBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRandomBinding.inflate(inflater, container, false)
        val rootView = binding.root
        progressBar = requireActivity().findViewById(R.id.main_search_progress)
        itemView = binding.searchResultItemLayout.searchResultItemLayout
        favoriteBtn = binding.searchResultItemLayout.itemFavoriteBtn

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRandomItem()
        updateViewsWithProgress()

        binding.randromBtn.setOnClickListener {
            viewModel.findRandomPokemon()
        }

        updateFavoriteButton()

        lifecycleScope.launchWhenStarted {
            viewModel.pokemon.collect { pokemon ->
                pokemon?.let {
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
    }

    private fun initRandomItem() {
        viewModel.setInitStatus()
        viewModel.findRandomPokemon()
    }

    private fun updateViewsWithProgress() {
        lifecycleScope.launchWhenStarted {
            viewModel.downloadState.collect { status ->
                when (status) {
                    Status.LOADING -> {
                        progressBar?.visibility = View.VISIBLE
                        itemView?.visibility = View.INVISIBLE
                    }
                    Status.SUCCESS -> {
                        progressBar?.visibility = View.GONE
                        itemView?.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        progressBar?.visibility = View.GONE
                        itemView?.visibility = View.VISIBLE
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.status_error_message),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    Status.INIT -> {
                        progressBar?.visibility = View.INVISIBLE
                        itemView?.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun updateFavoriteButton() {

        favoriteBtn?.setOnClickListener {
            viewModel.addFavorite()
            if (viewModel.addedState.value) {
                viewModel.setAddedState(false)
                findNavController().navigate(R.id.action_randomFragment_to_favoriteFragment)
            } else {
                viewModel.setAddedState(true)
            }
        }

        mainScope.launch {
            viewModel.addedState.collect { state ->
                when (state) {
                    true -> {
                        favoriteBtn?.setText(R.string.go_to_favorite_btn)
                        setColor(favoriteBtn, R.color.green_500)
                    }
                    false -> {
                        favoriteBtn?.setText(R.string.add_to_favorite_btn)
                        setColor(favoriteBtn, R.color.grey_800)
                    }
                }
            }
        }
    }

    private fun setColor(view: View?, color: Int) {
        view?.backgroundTintList = ContextCompat.getColorStateList(
            requireContext(), color
        )
    }

    private fun loadImage(uri: String, iv: ImageView) {
        view?.let {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.appbar_favorites_menu -> {
                view?.findNavController()?.navigate(R.id.action_randomFragment_to_favoriteFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        progressBar = null
    }

    override fun onDestroy() {
        super.onDestroy()
        ioScope.cancel()
        mainScope.cancel()
    }
}