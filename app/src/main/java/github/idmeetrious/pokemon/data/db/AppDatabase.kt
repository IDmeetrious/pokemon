package github.idmeetrious.pokemon.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import github.idmeetrious.pokemon.data.db.entities.PokemonData

@Database(entities = [PokemonData::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}