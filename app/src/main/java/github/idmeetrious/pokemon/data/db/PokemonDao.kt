package github.idmeetrious.pokemon.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import github.idmeetrious.pokemon.data.db.entities.PokemonData
import io.reactivex.rxjava3.core.Single

@androidx.room.Dao
interface PokemonDao: Dao<PokemonData> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePokemon(pokemon: PokemonData)

    @Query("SELECT * FROM pokemon")
    fun getPokemons(): Single<List<PokemonData>>

    @Delete
    fun removePokemon(pokemon: PokemonData)
}