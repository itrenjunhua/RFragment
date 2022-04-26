package com.renj.fragment.utils

import android.text.TextUtils
import android.util.Log

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2021-10-13   11:34
 *
 * 描述：Log 日志工具类
 *
 * 修订历史：
 *
 * ======================================================================
 */
object Logger {
    private const val DEFAULT_TAG = "Renj"
    private var tag = DEFAULT_TAG

    @JvmStatic
    fun tag(loggerTag: String) {
        tag = if (TextUtils.isEmpty(loggerTag)) DEFAULT_TAG else loggerTag
    }

    @JvmStatic
    fun i(msg: String?) {
        if (!TextUtils.isEmpty(msg)) Log.i(tag, msg!!)
    }

    @JvmStatic
    fun e(msg: String?) {
        if (!TextUtils.isEmpty(msg)) Log.e(tag, msg!!)
    }
}