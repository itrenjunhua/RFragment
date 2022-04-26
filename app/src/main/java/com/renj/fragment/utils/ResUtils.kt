package com.renj.fragment.utils

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import com.renj.fragment.MyApplication

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2021-11-18   9:49
 *
 * 描述：操作资源文件工具类
 *
 * 修订历史：
 *
 * ======================================================================
 */
object ResUtils {
    /**
     * 图片资源文件转为 uri
     *
     * @param resId 图片ID
     * @return 转换结果
     */
    @JvmStatic
    fun getResUri(resId: Int): String {
        val r: Resources = MyApplication.getApplication().resources
        val uri = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + r.getResourcePackageName(resId) + "/"
                    + r.getResourceTypeName(resId) + "/"
                    + r.getResourceEntryName(resId)
        )
        return uri.toString()
    }

    /**
     * 获取颜色值
     */
    @JvmStatic
    fun getColor(colorId: Int): Int {
        val r: Resources = MyApplication.getApplication().resources
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r.getColor(colorId, null)
        } else {
            r.getColor(colorId)
        }
    }

    @JvmStatic
    fun getString(stringId: Int): String {
        return MyApplication.getApplication().getString(stringId)
    }
}