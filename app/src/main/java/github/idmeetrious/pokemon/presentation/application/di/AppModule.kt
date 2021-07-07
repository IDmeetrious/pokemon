package github.idmeetrious.pokemon.presentation.application.di

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        DatabaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        UseCaseModule::class
    ]
)
class AppModule(private val context: Context) {

    @[Provides Singleton]
    fun provideApplicationContext(): Context = context

    companion object {
        @Singleton
        @Provides
        fun provideAppResources(context: Context): Resources {
            return context.resources
        }
    }
}