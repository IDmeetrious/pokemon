package github.idmeetrious.pokemon.domain.entities

data class Pokemon(
    val id: Long,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites
)
