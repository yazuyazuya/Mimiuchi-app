package com.example.mimiuchi.model


import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.MainRepository
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class MainRepositoryImpl:MainRepository{

    val gson = GsonBuilder()
        .serializeNulls()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://35.189.144.179/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpBuilder.build())//なくても動く
        .build()


    //なくても動く
    val httpBuilder: OkHttpClient.Builder get() {
        // create http client
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()

                //header
                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method(), original.body())
                    .build()

                return@Interceptor chain.proceed(request)
            })
            .readTimeout(30, TimeUnit.SECONDS)

        // log interceptor
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)

        return httpClient
    }

    override  fun initApiService(): ApiService = retrofit.create(ApiService::class.java)

}
