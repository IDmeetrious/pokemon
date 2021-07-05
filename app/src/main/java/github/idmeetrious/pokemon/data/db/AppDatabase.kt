package github.idmeetrious.pokemon.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PokemonData.Pokemon::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}