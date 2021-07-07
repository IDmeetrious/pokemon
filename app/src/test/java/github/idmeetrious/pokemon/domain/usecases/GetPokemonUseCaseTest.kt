package github.idmeetrious.pokemon.domain.usecases

import github.idmeetrious.pokemon.domain.entities.Pokemon
import github.idmeetrious.pokemon.domain.entities.Sprites
import github.idmeetrious.pokemon.domain.repositories.Repository
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class GetPokemonUseCaseTest {
    val repository = Mockito.mock(Repository::class.java)
    val getPokemonUseCase by lazy { GetPokemonUseCase(repository) }
    val sprites = Sprites("", "", "", "")
    val pokemon = Pokemon(1, "a", 1, 2, sprites)

    @Test
    fun `test getPokemonUseCase complete`(): Unit = runBlocking {
        `when`(repository.getPokemonRemote("1"))
            .thenReturn(Single.just(pokemon))
        getPokemonUseCase.invoke("1")
            .test()
            .assertComplete()
    }

    @Test
    fun `test getPokemonUseCase error`(): Unit = runBlocking {
        val error = Throwable("Error response")
        `when`(repository.getPokemonRemote(""))
            .thenReturn(Single.error(error))
        getPokemonUseCase.invoke("")
            .test()
            .assertError(error)
    }

    @Test
    fun `test getPokemonUseCase response`(): Unit = runBlocking {
        val pokemonN = pokemon
        pokemonN.name = "new"
        `when`(repository.getPokemonRemote(pokemonN.name))
            .thenReturn(Single.just(pokemonN))
        getPokemonUseCase.invoke(pokemonN.name)
            .test()
            .assertValue(pokemonN)
    }

}