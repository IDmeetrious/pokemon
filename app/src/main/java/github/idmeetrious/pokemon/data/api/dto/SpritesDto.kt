package github.idmeetrious.pokemon.data.api.dto

import com.google.gson.annotations.SerializedName

data class SpritesDto(
    @SerializedName("back_default")
    val backDefault: String = "",
    @SerializedName("back_shiny")
    val backShiny: String = "",
    @SerializedName("front_default")
    val frontDefault: String = "",
    @SerializedName("front_shiny")
    val frontShiny: String = ""
)