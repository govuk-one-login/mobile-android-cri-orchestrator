package uk.gov.onelogin.criorchestrator.testwrapper

import android.app.Application

class App : Application() {
    val objects: SingletonHolder by lazy {
        SingletonHolder(this)
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private lateinit var _instance: App
        val instance: App get() = _instance
    }
}
