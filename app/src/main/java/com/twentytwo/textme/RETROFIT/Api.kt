package com.twentytwo.textme.RETROFIT


import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("reguser")
    fun createUser(
        @Field("email")email: String,
        @Field("password") password: String,
        @Field("name") name:String,
        @Field("phone") phone:String
    ):Call<defaultresponse>


    @FormUrlEncoded
    @POST("lastseen")
    fun LastSceeen(
        @Field("uid") uid: String,
        @Field("lastseen")lastseen:String,
        @Field("touid")touid:String

    ):Observable<defaultresponse>
//===============================================?


}