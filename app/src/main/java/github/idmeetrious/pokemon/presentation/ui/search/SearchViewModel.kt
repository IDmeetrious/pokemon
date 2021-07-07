package github.idmeetrious.pokemon.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.idmeetrious.pokemon.domain.common.Status
import github.idmeetrious.pokemon.domain.entities.Pokemon
import github.idmeetrious.pokemon.domain.usecases.AddFavoriteUseCase
import github.idmeetrious.pokemon.domain.usecases.GetPokemonUseCase
import github.idmeetrious.pokemon.presentation.application.App
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

private const val TAG = "SearchViewModel"

class SearchViewModel : ViewModel() {
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

    init {
        App.appComponent.inject(this)
    }

    suspend fun findPokemon(name: String) = withContext(Dispatchers.IO) {
        _downloadState.emit(Status.LOADING)
        _uploadState.emit(Status.LOADING)
        disposable = getPokemonUseCase.invoke(name)
            .subscribeOn(Schedulers.io())
            .subscribe({
                viewModelScope.launch {
                    _downloadState.emit(Status.SUCCESS)
                    _pokemon.emit(it)
                }

            }, {
                viewModelScope.launch {
                    _downloadState.emit(Status.ERROR)
                }
            })
    }

    fun addFavorite() {
        pokemon.value?.let {
            viewModelScope.launch {
                _uploadState.emit(Status.LOADING)
                addFavoriteUseCase.invoke(it)
            }.invokeOnCompletion {
                viewModelScope.launch {
                    _uploadState.emit(Status.SUCCESS)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun findRandomPokemon() {
        val random = Random.nextInt(1, 1000)
        viewModelScope.launch {
            findPokemon("$random")
        }
    }
}