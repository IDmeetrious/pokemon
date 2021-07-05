package github.idmeetrious.pokemon.data.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class PokemonData {
    @Entity
    data class Pokemon(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        val id: Long,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "height")
        val height: Int,
        @ColumnInfo(name = "weight")
        val weight: Int,
        @Embedded
        val sprites: Sprites
    )

    @Entity
    data class Sprites(
        @ColumnInfo(name = "back_default")
        val backDefault: String,
        @ColumnInfo(name = "back_shiny")
        val backShiny: String,
        @ColumnInfo(name = "front_default")
        val frontDefault: String,
        @ColumnInfo(name = "front_shiny")
        val frontShiny: String
    )
}
