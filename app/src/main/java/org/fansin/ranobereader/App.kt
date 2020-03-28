package org.fansin.ranobereader

import android.app.Application
import org.fansin.ranobereader.di.AppComponent
import org.fansin.ranobereader.di.AppModule
import org.fansin.ranobereader.di.DaggerAppComponent
import org.fansin.ranobereader.di.FavoritesModule
import org.fansin.ranobereader.di.NetModule
import org.fansin.ranobereader.di.NovelModule

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
                .favoritesModule(FavoritesModule())
                .build()
    }
}
