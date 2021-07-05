package github.idmeetrious.pokemon.presentation.application.di.component

import dagger.Component
import github.idmeetrious.pokemon.presentation.application.di.AppModule
import github.idmeetrious.pokemon.presentation.application.di.RepositoryModule

@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent{

}