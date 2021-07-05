package github.idmeetrious.pokemon.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonData(
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
    val sprites: SpritesData
)