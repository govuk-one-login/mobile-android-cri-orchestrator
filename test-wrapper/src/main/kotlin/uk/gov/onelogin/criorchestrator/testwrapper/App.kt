package uk.gov.onelogin.criorchestrator.testwrapper

import android.app.Application

class App : Application() {
    lateinit var objects: SingletonHolder
        private set

    override fun onCreate() {
        super.onCreate()
        _instance = this
        objects = SingletonHolder(this)
    }

    companion object {
        private lateinit var _instance: App
        val instance: App get() = _instance
    }
}
