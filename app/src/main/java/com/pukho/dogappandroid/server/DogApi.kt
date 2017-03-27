package com.pukho.dogappandroid.server

import com.pukho.dogappandroid.model.Dog
import retrofit2.Call
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*
import retrofit2.http.GET




/**
 * Created by Pukho on 06.03.2017.
 */

interface DogApi {

    @GET("/dog/{id}")
    fun getDog(@Path("id") id : Long) : Call<Dog>

    @GET("/dog/")
    fun getDogs() : Call<MutableList<Dog>?>

    @Multipart
    @POST("/dog/{id}")
    fun uploadDog (
            @Path("id") id : Long,
            @Part("dog") dog: Dog,
            @Part imageDog : MultipartBody.Part?): Call<Dog>

    @GET("/picture/{id}")
    @Streaming
    fun downloadFileUri(@Path("id") id : Long): Call<ResponseBody>

    @Multipart
    @POST("/dog/")
    fun createDog (
            @Part("dog") dog : Dog,
            @Part ImageDog : MultipartBody.Part?): Call<Dog>


    @DELETE("/dog/{id}")
    fun deleteDog(
            @Path("id") id : Long
    ) : Call<ResponseBody>
}