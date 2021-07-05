package github.idmeetrious.pokemon.data.api.dto

import com.google.gson.annotations.SerializedName

data class PokemonDto(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("height")
    val height: Int = 0,
    @SerializedName("weight")
    val weight: Int = 0,
    @SerializedName("sprites")
    val sprites: SpritesDto
)
