package github.idmeetrious.pokemon.presentation.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.idmeetrious.pokemon.domain.common.Status
import github.idmeetrious.pokemon.domain.entities.Pokemon
import github.idmeetrious.pokemon.domain.usecases.AddFavoriteUseCase
import github.idmeetrious.pokemon.domain.usecases.DeleteFavoriteUseCase
import github.idmeetrious.pokemon.domain.usecases.GetPokemonUseCase
import github.idmeetrious.pokemon.domain.usecases.LoadFavoritesUseCase
import github.idmeetrious.pokemon.presentation.application.App
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SearchViewModel"

class SearchViewModel : ViewModel() {
    @Inject
    lateinit var getPokemonUseCase: GetPokemonUseCase

    @Inject
    lateinit var addFavoriteUseCase: AddFavoriteUseCase

    @Inject
    lateinit var loadFavoritesUseCase: LoadFavoritesUseCase

    @Inject
    lateinit var deleteFavoriteUseCase: DeleteFavoriteUseCase

    private var disposable: Disposable? = null

    private var _pokemon: MutableStateFlow<Pokemon?> = MutableStateFlow(null)
    val pokemon get() = _pokemon

    private var _favorites: MutableStateFlow<List<Pokemon>> = MutableStateFlow(emptyList())
    val favorites get() = _favorites

    private var _downloadState: MutableStateFlow<Status> = MutableStateFlow(Status.INIT)
    val downloadState get() = _downloadState

    private var _uploadState: MutableStateFlow<Status> = MutableStateFlow(Status.INIT)
    val uploadState get() = _uploadState

    private var _addedState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val addedState get() = _addedState

    init {
        App.appComponent.inject(this)
    }

    fun findPokemon(name: String) {
        viewModelScope.launch {
            _downloadState.value = Status.LOADING
            _uploadState.value = Status.LOADING

            disposable = getPokemonUseCase.invoke(name)
                .doAfterSuccess {
                    checkInFavorite()
                }
                .subscribe({
                    _downloadState.value = Status.SUCCESS
                    _pokemon.value = it
                }, {
                    _downloadState.value = Status.ERROR
                })
        }
    }

    fun addFavorite() {
        viewModelScope.launch {
            _uploadState.value = Status.LOADING

            pokemon.value?.let { poke ->
                viewModelScope.launch {
                    addFavoriteUseCase.invoke(poke)
                }.invokeOnCompletion {
                    _uploadState.value = Status.SUCCESS
                }
            }
        }
    }

    fun checkInFavorite() = CoroutineScope(Dispatchers.IO).launch {
        Log.i(TAG, "--> checkInFavorite: $pokemon")
        pokemon.value?.let { pokemon ->
            loadFavoritesUseCase.invoke()
                .doOnSuccess {
                    viewModelScope.launch {
                        _addedState.tryEmit(it.find { poke -> poke == pokemon } != null)
                    }
                }
                .subscribe({}, {
                    Log.e(TAG, "--> checkInFavorite: ${it.message}")
                })
        }


    }

    fun setInitStatus() {
        viewModelScope.launch {
            _downloadState.value = Status.INIT
        }
    }

    fun setAddedState(state: Boolean) {
        viewModelScope.launch {
            _addedState.value = state
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}