package github.idmeetrious.pokemon.application.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import github.idmeetrious.pokemon.application.di.qualifier.ApplicationContext
import github.idmeetrious.pokemon.data.db.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "pokemon_database")
            .build()

    @Singleton
    @Provides
    fun providePokemonDao(appDatabase: AppDatabase) = appDatabase.pokemonDao()
}