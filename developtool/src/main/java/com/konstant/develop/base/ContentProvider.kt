package com.konstant.develop.base

import android.app.Application
import kotlin.properties.Delegates

object ContentProvider {

    private var app: Application by Delegates.notNull()

    fun saveApplication(application: Application) {
        this.app = application
    }

    fun getApplication(): Application {
        return app
    }

}