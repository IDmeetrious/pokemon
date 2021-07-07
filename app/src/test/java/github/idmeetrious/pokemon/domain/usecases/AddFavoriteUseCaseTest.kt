package github.idmeetrious.pokemon.domain.usecases

import github.idmeetrious.pokemon.domain.entities.Pokemon
import github.idmeetrious.pokemon.domain.entities.Sprites
import github.idmeetrious.pokemon.domain.repositories.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class AddFavoriteUseCaseTest {

    val repository = Mockito.mock(Repository::class.java)
    val addFavoriteUseCase by lazy { AddFavoriteUseCase(repository) }
    val sprites = Sprites("", "", "", "")
    val pokemon = Pokemon(1, "a", 1, 2, sprites)

    @Test
    fun `test addFavoriteUseCase`() = runBlocking {
        Mockito.`when`(repository.addFavorite(pokemon)).then {
            print("Successfully added")
        }
        addFavoriteUseCase.invoke(pokemon)
    }

}