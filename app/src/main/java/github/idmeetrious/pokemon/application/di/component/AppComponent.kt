package github.idmeetrious.pokemon.application.di.component

import dagger.Component
import github.idmeetrious.pokemon.application.di.AppModule
import github.idmeetrious.pokemon.application.di.RepositoryModule

@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent{

}