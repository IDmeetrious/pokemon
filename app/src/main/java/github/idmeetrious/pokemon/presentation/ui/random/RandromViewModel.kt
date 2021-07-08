package github.idmeetrious.pokemon.presentation.ui.random

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.idmeetrious.pokemon.domain.common.Status
import github.idmeetrious.pokemon.domain.entities.Pokemon
import github.idmeetrious.pokemon.domain.usecases.AddFavoriteUseCase
import github.idmeetrious.pokemon.domain.usecases.GetPokemonUseCase
import github.idmeetrious.pokemon.presentation.application.App
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.launch
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

private const val TAG = "RandomViewModel"

class RandomViewModel : ViewModel() {
    @Inject
    lateinit var getPokemonUseCase: GetPokemonUseCase

    @Inject
    lateinit var addFavoriteUseCase: AddFavoriteUseCase

    private var disposable: Disposable? = null

    private var _pokemon: MutableStateFlow<Pokemon?> = MutableStateFlow(null)
    val pokemon get() = _pokemon

    private var _downloadState: MutableStateFlow<Status> = MutableStateFlow(Status.INIT)
    val downloadState get() = _downloadState

    private var _uploadState: MutableStateFlow<Status> = MutableStateFlow(Status.INIT)
    val uploadState get() = _uploadState

    private var _addedState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val addedState get() = _addedState

    init {
        App.appComponent.inject(this)
    }

    private fun findPokemon(name: String) {
        viewModelScope.launch {
            _downloadState.value = Status.LOADING
            _uploadState.value = Status.LOADING

            disposable = getPokemonUseCase.invoke(name)
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

    fun findRandomPokemon() {
        val random = Random.nextInt(1, 1000)
        viewModelScope.launch {
            findPokemon("$random")
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