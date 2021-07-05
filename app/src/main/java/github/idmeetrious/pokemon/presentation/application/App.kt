package github.idmeetrious.pokemon.presentation.application

import android.app.Application
import github.idmeetrious.pokemon.presentation.application.di.component.AppComponent
import github.idmeetrious.pokemon.presentation.application.di.component.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .build()
    }
}