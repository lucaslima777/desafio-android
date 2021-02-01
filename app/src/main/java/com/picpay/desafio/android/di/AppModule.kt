package com.picpay.desafio.android.di

import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.local.CacheInterceptor
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.repository.UserDataSource
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.view.UserViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

internal const val URL = "http://careers.picpay.com/tests/mobdev/"
internal const val SIZE = 10 * 1024 * 1024
internal const val NAME = "samplecache"

val appModule = module {
    single {
        Cache(File(androidContext().cacheDir, NAME), SIZE.toLong())
    }
    single {
        OkHttpClient.Builder()
            .addNetworkInterceptor(CacheInterceptor())
            .cache(get())
            .build()
    }
    single {
        GsonBuilder().create()
    }
    single {
        Retrofit.Builder()
            .baseUrl(URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
    single {
        get<Retrofit>().create(PicPayService::class.java)
    }
    single<UserRepository> { UserDataSource(get()) }
    viewModel { UserViewModel.ViewModelFactory(get()).create(UserViewModel::class.java) }
}