package github.idmeetrious.pokemon.application.di

import android.content.Context
import android.content.res.Resources
import dagger.Binds
import dagger.Module
import dagger.Provides
import github.idmeetrious.pokemon.application.App
import github.idmeetrious.pokemon.application.di.qualifier.ApplicationContext
import javax.inject.Singleton

@Module(includes = [
    DatabaseModule::class,
    NetworkModule::class,
    RepositoryModule::class
])
abstract class AppModule {

    @ApplicationContext
    @Binds
    abstract fun provideApplicationContext(app: App): Context

    companion object{
        @Singleton
        @Provides
        fun provideAppResources(context: Context): Resources{
            return context.resources
        }
    }
}