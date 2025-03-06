package com.example.citationeapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.citationeapp.data.remote.api.CitationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CitationModule {
    private const val BASE_URL = "http://10.35.21.2:8080/api/"
    // private const val BASE_URL = "http://192.168.1.90:8080/api/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCitationApiService(retrofit: Retrofit): CitationApiService {
        return retrofit.create(CitationApiService::class.java)
    }
}
