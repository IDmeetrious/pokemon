package github.idmeetrious.pokemon.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "sprites")
data class SpritesData(
    @ColumnInfo(name = "back_default")
    val backDefault: String,
    @ColumnInfo(name = "back_shiny")
    val backShiny: String,
    @ColumnInfo(name = "front_default")
    val frontDefault: String,
    @ColumnInfo(name = "front_shiny")
    val frontShiny: String
)