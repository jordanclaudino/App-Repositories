package me.dio.projetofinal

import android.app.Application
import me.dio.projetofinal.data.di.DataModule
import me.dio.projetofinal.domain.di.DomainModule
import me.dio.projetofinal.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@App)
        }

        DataModule.load()
        DomainModule.load()
        PresentationModule.load()
    }

}