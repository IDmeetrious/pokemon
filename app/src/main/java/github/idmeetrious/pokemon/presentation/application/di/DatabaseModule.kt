package github.idmeetrious.pokemon.presentation.application.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import github.idmeetrious.pokemon.data.datasource.local.LocalDataSource
import github.idmeetrious.pokemon.data.datasource.local.LocalDataSourceImpl
import github.idmeetrious.pokemon.data.db.AppDatabase
import github.idmeetrious.pokemon.data.db.PokemonDao
import github.idmeetrious.pokemon.data.mappers.DbToEntityMapper
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideDatabase() =
        Room.databaseBuilder(context, AppDatabase::class.java, "pokemon_database")
            .build()

    @Singleton
    @Provides
    fun providePokemonDao(appDatabase: AppDatabase) = appDatabase.pokemonDao()

    @Provides
    fun provideLocalDataSource(dao: PokemonDao): LocalDataSource =
        LocalDataSourceImpl(dao, DbToEntityMapper())
}