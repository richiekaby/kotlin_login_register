package net.larntech.loginregister.network.retrofit

import net.larntech.loginregister.network.config.BaseConfig
import net.larntech.loginregister.network.services.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val retrofit: Retrofit
    get()
    {
        return Retrofit.Builder()
            .baseUrl(BaseConfig.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()

    }

    val apiService: ApiService = retrofit.create(ApiService::class.java)


    private fun getOkHttpClient(): OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
            .addInterceptor{
                chain ->
                val newRequest = chain.request().newBuilder()
                    .build()
                chain.proceed(newRequest)
            }
        return builder.build()
    }



}