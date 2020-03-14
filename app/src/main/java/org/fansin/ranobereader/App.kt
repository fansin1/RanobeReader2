package org.fansin.ranobereader

import android.app.Application
import org.fansin.ranobereader.di.*

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerAppComponent
                .builder()
                .appModule(AppModule(applicationContext))
                .netModule(NetModule())
                .novelModule(NovelModule())
                .build()
    }
}
