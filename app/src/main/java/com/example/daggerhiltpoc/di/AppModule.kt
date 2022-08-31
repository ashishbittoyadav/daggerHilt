package com.example.daggerhiltpoc.di

import android.content.Context
import androidx.room.Room
import com.example.daggerhiltpoc.BuildConfig
import com.example.daggerhiltpoc.api.ApiHelper
import com.example.daggerhiltpoc.api.ApiHelperImpl
import com.example.daggerhiltpoc.api.ApiService
import com.example.daggerhiltpoc.room_db.AppDataBase
import com.example.daggerhiltpoc.room_db.UserDao
import com.example.daggerhiltpoc.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesString() = Constants.BASE_URL

    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Provides
    fun provideUserDao(appDataBase: AppDataBase) : UserDao = appDataBase.userDao()

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "RssReader"
        ).build()
    }

//    @Provides
//    fun providesViewModelRepo() = TestViewModelRepository()
}