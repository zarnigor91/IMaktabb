package com.example.imaktab.network

import com.example.imaktab.App
import com.example.imaktab.TOKEN_KEY
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val apiClinet: IApiClient by lazy {
        val retrofit = initRetrofit()
        retrofit.create(IApiClient::class.java)
    }

    private fun initRetrofit(): Retrofit {
        val settings =
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(App.getApplication())

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(ChuckInterceptor(App.getApplication()))
            .addInterceptor {
                val key = settings.getString(TOKEN_KEY, null)

                if (key!=null) {
                    val request = it
                        .request()
                        .newBuilder()
                        .addHeader("Authorization", "Token $key")
                        .build()
                    return@addInterceptor it.proceed(request)
                }

                return@addInterceptor it.proceed(it.request())
            }
            .build()


        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}