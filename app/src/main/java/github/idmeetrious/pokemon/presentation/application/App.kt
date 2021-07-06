package github.idmeetrious.pokemon.presentation.application

import android.app.Application
import github.idmeetrious.pokemon.presentation.application.di.AppModule
import github.idmeetrious.pokemon.presentation.application.di.DatabaseModule
import github.idmeetrious.pokemon.presentation.application.di.component.AppComponent
import github.idmeetrious.pokemon.presentation.application.di.component.DaggerAppComponent
import javax.inject.Singleton

@Singleton
class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .databaseModule(DatabaseModule(applicationContext))
            .build()
    }
}