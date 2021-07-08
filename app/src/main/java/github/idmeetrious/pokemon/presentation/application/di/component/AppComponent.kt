package github.idmeetrious.pokemon.presentation.application.di.component

import dagger.Component
import github.idmeetrious.pokemon.presentation.application.di.AppModule
import github.idmeetrious.pokemon.presentation.ui.favorite.FavoriteViewModel
import github.idmeetrious.pokemon.presentation.ui.random.RandomViewModel
import github.idmeetrious.pokemon.presentation.ui.search.SearchViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(searchViewModel: SearchViewModel)
    fun inject(randomViewModel: RandomViewModel)
    fun inject(favoriteViewModel: FavoriteViewModel)
}