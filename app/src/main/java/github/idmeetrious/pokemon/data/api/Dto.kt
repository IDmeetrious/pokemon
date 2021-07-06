package github.idmeetrious.pokemon.data.api

import com.google.gson.annotations.SerializedName

sealed class Dto {
    data class Pokemon(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("name")
        val name: String = "",
        @SerializedName("height")
        val height: Int = 0,
        @SerializedName("weight")
        val weight: Int = 0,
        @SerializedName("sprites")
        val sprites: Sprites? = null
    ) : Dto()

    data class Sprites(
        @SerializedName("back_default")
        val backDefault: String = "",
        @SerializedName("back_shiny")
        val backShiny: String = "",
        @SerializedName("front_default")
        val frontDefault: String = "",
        @SerializedName("front_shiny")
        val frontShiny: String = ""
    )
}
