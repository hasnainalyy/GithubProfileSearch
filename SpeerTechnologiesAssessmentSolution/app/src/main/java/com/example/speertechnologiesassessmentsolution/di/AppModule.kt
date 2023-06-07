package com.example.speertechnologiesassessmentsolution.di


import com.example.speertechnologiesassessmentsolution.domain.repository.Repository
import com.example.speertechnologiesassessmentsolution.network.ApiService
import com.example.speertechnologiesassessmentsolution.utils.AppConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.google.gson.Gson
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideApi(): ApiService {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.callTimeout(1, TimeUnit.MINUTES)
        httpClient.connectTimeout(20, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(30, TimeUnit.SECONDS)
        httpClient.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
            val request = requestBuilder.build()
            chain.proceed(request)
        })
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(AppConstant.ServerAPICalls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideRepository(api: ApiService): Repository {
        return Repository(api)
    }

}