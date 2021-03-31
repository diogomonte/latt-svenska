package com.dom.lattsvenska

import android.app.Application
import com.dom.lattsvenska.database.AppDatabase

class LattSvenskaApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        AppDatabase.getInstance(context = this@LattSvenskaApplication)
    }
}