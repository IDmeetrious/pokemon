package github.idmeetrious.pokemon.data.db

import github.idmeetrious.pokemon.domain.entities.Pokemon

@androidx.room.Dao
interface PokemonDao: Dao<PokemonData.Pokemon> {
}