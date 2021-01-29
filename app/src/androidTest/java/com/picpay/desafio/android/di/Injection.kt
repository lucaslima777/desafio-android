package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.remote.PicPayService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun appModule(url: String) = module {
    single(override = true) {
        Retrofit.Builder()
            .baseUrl(url)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
    single(override = true) {
        get<Retrofit>().create(PicPayService::class.java)
    }
}