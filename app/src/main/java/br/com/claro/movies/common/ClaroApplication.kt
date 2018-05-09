package br.com.claro.movies.common

import android.app.Application
import br.com.claro.movies.BuildConfig
import br.com.claro.movies.dagger.AppComponent
import br.com.claro.movies.dagger.AppModule
import br.com.claro.movies.dagger.DaggerAppComponent
import com.facebook.stetho.Stetho
import timber.log.Timber

class ClaroApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        Timber.plant(if (BuildConfig.DEBUG) DebugLog() else ReleaseLog())
        instance = this
        component = DaggerAppComponent.builder()
                .appModule(AppModule())
                .build()
    }

    companion object {

        lateinit var instance: Application
            private set

        lateinit var component: AppComponent
            private set

        var apiUrl: String = BuildConfig.API_URL
    }
}