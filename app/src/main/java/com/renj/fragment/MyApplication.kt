package com.renj.fragment

import android.app.Application

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2021-10-13   17:46
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        lateinit var application: Application
            private set
    }
}