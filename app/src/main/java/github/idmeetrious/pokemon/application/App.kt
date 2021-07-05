package github.idmeetrious.pokemon.application

import android.app.Application
import github.idmeetrious.pokemon.application.di.component.AppComponent
import github.idmeetrious.pokemon.application.di.component.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .build()
    }
}