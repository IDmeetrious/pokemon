package github.idmeetrious.pokemon.domain.common

sealed class Result<out R> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: String): Result<Nothing>()
    object Loading : Result<Nothing>()
}
