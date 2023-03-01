package net.larntech.loginregister.network.services

import net.larntech.loginregister.network.model.login.LoginResponse
import net.larntech.loginregister.network.model.register.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //login and register call
    @GET("login.php")
    fun loginUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<LoginResponse>


    @FormUrlEncoded
    @POST("register.php")
    fun registerUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<RegisterResponse>
}