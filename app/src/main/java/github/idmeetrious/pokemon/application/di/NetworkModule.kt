package github.idmeetrious.pokemon.application.di

import dagger.Module
import dagger.Provides
import github.idmeetrious.pokemon.data.api.PokeApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    @Provides
    fun providePokeApi(retrofit: Retrofit): PokeApi =
        retrofit.create(PokeApi::class.java)

}