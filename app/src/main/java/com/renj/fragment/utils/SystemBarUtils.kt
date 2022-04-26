package com.renj.fragment.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorRes
import com.renj.fragment.R
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 *
 *
 * 创建时间：2020-03-27   21:43
 *
 *
 * 描述：状态栏和导航栏相关工具类<br></br>
 * 当使用了部分效果之后，状态栏将不会占位，导致布局文件和状态栏重复，<br></br>
 * 请在布局文件中不要覆盖在状态栏的布局部分的根布局增加 `android:fitsSystemWindows="true"` 属性
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
object SystemBarUtils {
    /**
     * 设置状态栏白色、暗模式且状态栏不占用内容区域
     *
     * @param activity [Activity]
     */
    @JvmStatic
    fun setStatusWhiteAndDark(activity: Activity) {
        setSystemBarColor(activity, R.color.white)
        setStatusBarDark(activity, true)
        setFitsSystemWindows(activity, true)
    }

    /**
     * 设置状态栏和导航栏白色、暗模式且状态栏不占用内容区域
     *
     * @param activity      [Activity]
     * @param navigationBar 导航栏是否白色
     */
    @JvmStatic
    fun setStatusWhiteAndDark(activity: Activity, navigationBar: Boolean) {
        setSystemBarColor(activity, R.color.white, navigationBar)
        setStatusBarDark(activity, true)
        setFitsSystemWindows(activity, true)
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return
     */
    @JvmStatic
    fun getStatusBarHeight(context: Context): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val resources = context.resources
            val resourceId =
                resources.getIdentifier("status_bar_height", "dimen", "android")
            resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }

    /**
     * 修改状态栏和导航栏为全透明,4,4以上生效
     *
     * @param activity      [Activity]
     * @param navigationBar 导航栏是否透明
     */
    /**
     * 修改状态栏为全透明,4,4以上生效
     *
     * @param activity [Activity]
     */
    @JvmOverloads
    @JvmStatic
    fun transparencySystemBar(activity: Activity, navigationBar: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            if (navigationBar) window.navigationBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = activity.window
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
    }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     *
     * @param activity [Activity]
     * @param colorId  颜色值id
     */
    @JvmStatic
    fun setSystemBarColor(activity: Activity, @ColorRes colorId: Int) {
        setSystemBarColor(activity, colorId, false)
    }

    /**
     * 修改状态栏和导航栏颜色，支持4.4以上版本
     *
     * @param activity [Activity]
     * @param colorId  颜色值id
     * @param colorId  导航栏是否一起设置
     */
    @JvmStatic
    fun setSystemBarColor(activity: Activity, @ColorRes colorId: Int, navigationBar: Boolean) {
        val window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = activity.resources.getColor(colorId)
        if (navigationBar) window.navigationBarColor = activity.resources.getColor(colorId)
    }

    /**
     * 修改状态栏和导航栏颜色，支持4.4以上版本
     *
     * @param activity             [Activity]
     * @param statusColorId        状态栏颜色值id
     * @param navigationBarColorId 导航栏颜色值id
     */
    @JvmStatic
    fun setSystemBarColor(
        activity: Activity,
        @ColorRes statusColorId: Int,
        @ColorRes navigationBarColorId: Int
    ) {
        val window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = activity.resources.getColor(statusColorId)
        window.navigationBarColor = activity.resources.getColor(navigationBarColorId)
    }

    /**
     * 修改状态栏文字颜色，是否黑色文字
     */
    @JvmStatic
    fun setStatusBarDark(activity: Activity, dark: Boolean) {
        when (RomUtils.lightStatusBarAvailableRomType) {
            RomUtils.AvailableRomType.MIUI -> setMIUIStatusBarLightMode(activity, dark)
            RomUtils.AvailableRomType.FLYME -> setFlymeLightStatusBar(activity, dark)
            RomUtils.AvailableRomType.ANDROID_NATIVE -> setAndroidStatusBar(activity, dark)
        }
    }

    /**
     * 设置状态栏与内容自适应
     */
    @JvmStatic
    fun setFitsSystemWindows(activity: Activity, isFitsSystemWindows: Boolean) {
        val contentView = activity.findViewById<ViewGroup>(android.R.id.content) ?: return
        if (contentView.childCount > 0) {
            val pageView = contentView.getChildAt(0)
            if (pageView != null) {
                pageView.fitsSystemWindows = isFitsSystemWindows
            }
        }
    }

    /**
     * 小米修改状态栏文字颜色是否为黑色
     */
    @JvmStatic
    private fun setMIUIStatusBarLightMode(activity: Activity, dark: Boolean): Boolean {
        var result = false
        val window = activity.window
        if (window != null) {
            val clazz: Class<*> = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod(
                    "setExtraFlags",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag) //状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag) //清除黑色字体
                }
                result = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && RomUtils.isMiUIV7OrAbove) {
                    // 开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        activity.window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    }
                }
            } catch (e: Exception) {
            }
        }
        return result
    }

    /**
     * 魅族修改状态栏文字是否为黑色
     */
    @JvmStatic
    private fun setFlymeLightStatusBar(activity: Activity?, dark: Boolean): Boolean {
        var result = false
        if (activity != null) {
            try {
                val lp = activity.window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                value = if (dark) {
                    value or bit
                } else {
                    value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                activity.window.attributes = lp
                result = true
            } catch (e: Exception) {
            }
        }
        return result
    }

    /**
     * Android原生修改状态栏文字是否为黑色
     */
    @JvmStatic
    private fun setAndroidStatusBar(activity: Activity, dark: Boolean) {
        val decor = activity.window.decorView
        if (dark) {
            decor.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    /**
     * 获取导航栏高度
     */
    @JvmStatic
    fun getNavigationBarHeight(context: Context): Int {
        var result = 0
        val res = context.resources
        val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * 品牌、系统等辅助类
     */
    private object RomUtils {
        // 开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错
        val lightStatusBarAvailableRomType: Int
            get() {
                // 开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错
                if (isMiUIV7OrAbove) {
                    return AvailableRomType.ANDROID_NATIVE
                }
                if (isMiUIV6OrAbove) {
                    return AvailableRomType.MIUI
                }
                if (isFlymeV4OrAbove) {
                    return AvailableRomType.FLYME
                }
                return if (isAndroidMOrAbove) {
                    AvailableRomType.ANDROID_NATIVE
                } else AvailableRomType.NA
            }//版本号4以上，形如4.x.

        // Flyme V4的displayId格式为 [Flyme OS 4.x.x.xA]
        // Flyme V5的displayId格式为 [Flyme 5.x.x.x beta]
        private val isFlymeV4OrAbove: Boolean
            private get() {
                val displayId = Build.DISPLAY
                if (!TextUtils.isEmpty(displayId) && displayId.contains("Flyme")) {
                    val displayIdArray = displayId.split(" ").toTypedArray()
                    for (temp in displayIdArray) {
                        //版本号4以上，形如4.x.
                        if (temp.matches(Regex.fromLiteral("^[4-9]\\.(\\d+\\.)+\\S*"))) {
                            return true
                        }
                    }
                }
                return false
            }

        // Android Api 23以上
        private val isAndroidMOrAbove: Boolean
            private get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        private const val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code"
        private val isMiUIV6OrAbove: Boolean
            private get() = try {
                val properties = Properties()
                properties.load(FileInputStream(File(Environment.getRootDirectory(), "build.prop")))
                val uiCode = properties.getProperty(KEY_MIUI_VERSION_CODE, null)
                if (uiCode != null) {
                    val code = uiCode.toInt()
                    code >= 4
                } else {
                    false
                }
            } catch (e: Exception) {
                false
            }
        val isMiUIV7OrAbove: Boolean
            get() = try {
                val properties = Properties()
                properties.load(FileInputStream(File(Environment.getRootDirectory(), "build.prop")))
                val uiCode = properties.getProperty(KEY_MIUI_VERSION_CODE, null)
                if (uiCode != null) {
                    val code = uiCode.toInt()
                    code >= 5
                } else {
                    false
                }
            } catch (e: Exception) {
                false
            }

        internal object AvailableRomType {
            const val MIUI = 1
            const val FLYME = 2
            const val ANDROID_NATIVE = 3
            const val NA = 4
        }
    }
}