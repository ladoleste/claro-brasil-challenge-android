package br.com.claro.movies.dagger

import android.arch.persistence.room.Room
import br.com.claro.movies.BuildConfig
import br.com.claro.movies.common.ClaroApplication
import br.com.claro.movies.common.ClaroApplication.Companion.apiUrl
import br.com.claro.movies.repository.MoviesRepository
import br.com.claro.movies.repository.MoviesRepositoryImpl
import br.com.claro.movies.repository.room.ClaroDatabase
import br.com.claro.movies.service.MoviesService
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

/**
 * Created by Anderson on 21/03/2018
 */
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    @Provides
    fun provideLogger(): HttpLoggingInterceptor {

        val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { mensagem -> Timber.tag("OkHttp").d(mensagem); })
        @Suppress("ConstantConditionIf")
        logger.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
        return logger
    }

    @Provides
    @Singleton
    fun provideOkHttp(logger: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideApi(gson: Gson, client: OkHttpClient): MoviesService {

        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()

        return retrofit.create(MoviesService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(): MoviesRepository = MoviesRepositoryImpl()

    @Provides
    @Singleton
    fun provideDatabase() = Room.databaseBuilder(ClaroApplication.instance, ClaroDatabase::class.java, "moviedb")
            .allowMainThreadQueries()
            .build()
}