package github.idmeetrious.pokemon.presentation.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.idmeetrious.pokemon.domain.common.Status
import github.idmeetrious.pokemon.domain.entities.Pokemon
import github.idmeetrious.pokemon.domain.usecases.DeleteFavoriteUseCase
import github.idmeetrious.pokemon.domain.usecases.LoadFavoritesUseCase
import github.idmeetrious.pokemon.presentation.application.App
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel : ViewModel() {

    @Inject
    lateinit var loadFavoritesUseCase: LoadFavoritesUseCase

    @Inject
    lateinit var deleteFavoriteUseCase: DeleteFavoriteUseCase

    private var disposable: Disposable? = null

    private var _favorites: MutableStateFlow<List<Pokemon>> = MutableStateFlow(emptyList())
    val favorites get() = _favorites

    private var _downloadState: MutableStateFlow<Status> = MutableStateFlow(Status.INIT)
    val downloadState get() = _downloadState

    private var _queryState: MutableStateFlow<Status> = MutableStateFlow(Status.INIT)
    val queryState get() = _queryState

    init {
        App.appComponent.inject(this)
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _downloadState.emit(Status.LOADING)
            disposable = loadFavoritesUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    viewModelScope.launch {
                        _downloadState.emit(Status.SUCCESS)
                        _favorites.emit(it)
                    }
                }, {
                    viewModelScope.launch {
                        _downloadState.emit(Status.ERROR)
                    }
                })
        }
    }

    fun deleteFavorite(pokemon: Pokemon) {
        viewModelScope.launch {
            _queryState.emit(Status.LOADING)
            deleteFavoriteUseCase.invoke(pokemon)
        }.invokeOnCompletion {
            if (it == null) {
                viewModelScope.launch {
                    _queryState.emit(Status.SUCCESS)
                }
            } else {
                viewModelScope.launch {
                    _queryState.emit(Status.ERROR)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}