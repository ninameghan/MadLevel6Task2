package com.example.madlevel6task2.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApi {

    //use companion object so method is statically accessible to all classes
    companion object {
        //Base url of the api
        private const val baseurl = "https://api.themoviedb.org/3/discover/"

        /**
         * @return [MovieApiService] The service class of the retrofit client
         */
        fun createApi(): MovieApiService {
            //Create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            //Create the Retrofit instance
            val movieApi = Retrofit.Builder()
                .baseUrl(baseurl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            //Return the Retrofit MovieApiService
            return movieApi.create(MovieApiService::class.java)
        }
    }
}