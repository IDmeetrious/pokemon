package github.idmeetrious.pokemon.domain.entities

data class Pokemon(
    val id: Int,
    var name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites
)
