package com.pukho.dogappandroid.server

import com.pukho.dogappandroid.server.DogApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.converter.jackson.JacksonConverterFactory


/**
 * Created by Pukho on 09.03.2017.
 */

class DogService private constructor() {

    var logging = HttpLoggingInterceptor()

    var httpClient = OkHttpClient.Builder()

    private object Holder { val INSTANCE = DogService().getDogApi("http://192.168.2.105:8082") }

    companion object {
        val api: DogApi by lazy { Holder.INSTANCE }
    }

    init {
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging);
    }

    fun getDogApi(url : String): DogApi {
        val retrofit : Retrofit = Retrofit.Builder()
                .baseUrl(url) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build()

        return retrofit.create(DogApi::class.java);
    }
}