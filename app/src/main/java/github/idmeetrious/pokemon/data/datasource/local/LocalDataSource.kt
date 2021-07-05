package github.idmeetrious.pokemon.data.datasource.local

interface LocalDataSource {
    suspend fun add()
    suspend fun read()
    suspend fun remove()
}