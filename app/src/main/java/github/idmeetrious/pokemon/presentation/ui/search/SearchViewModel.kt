package github.idmeetrious.pokemon.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.idmeetrious.pokemon.domain.common.Status
import github.idmeetrious.pokemon.domain.entities.Pokemon
import github.idmeetrious.pokemon.domain.usecases.GetPokemonUseCase
import github.idmeetrious.pokemon.presentation.application.App
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "SearchViewModel"

class SearchViewModel : ViewModel() {
    @Inject
    lateinit var getPokemonUseCase: GetPokemonUseCase
    private var disposable: Disposable? = null

    private var _pokemon: MutableStateFlow<Pokemon?> = MutableStateFlow(null)
    val pokemon get() = _pokemon

    private var _status: MutableStateFlow<Status> = MutableStateFlow(Status.SUCCESS)
    val status get() = _status

    init {
        App.appComponent.inject(this)
    }

    suspend fun findPokemon(name: String) = withContext(Dispatchers.IO) {
        _status.emit(Status.LOADING)
        disposable = getPokemonUseCase.invoke(name)
            .subscribeOn(Schedulers.io())
            .subscribe({
                viewModelScope.launch {
                    _status.emit(Status.SUCCESS)
                    _pokemon.emit(it)
                }

            }, {
                viewModelScope.launch {
                    _status.emit(Status.ERROR)
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}