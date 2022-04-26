package com.renj.fragment.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import com.renj.fragment.MyApplication

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 *
 *
 * 创建时间：2017-07-06   18:09
 *
 *
 * 描述：与界面以及UI线程相关的工具类，包含获取全局的Context，单位转换；<br></br>
 * 自主线程执行Runnable、获取主线程对象、MainHandler、MainLooper、弹出Toast等
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
object UIUtils {
    /**
     * 获取全局的上下文
     *
     * @return 全局的上下文
     */
    @JvmStatic
    val context: Context?
        get() = MyApplication.getApplication()

    /**
     * dip转换成px
     *
     * @param dipValue
     * @return px值
     */
    @JvmStatic
    fun dip2px(dipValue: Float): Int {
        val scale = context!!.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * px转换成dip
     *
     * @param pxValue
     * @return dp值
     */
    @JvmStatic
    fun px2dip(pxValue: Float): Int {
        val scale = context!!.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * sp转换成px
     */
    @JvmStatic
    fun sp2px(spValue: Float): Int {
        val fontScale = context!!.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * px转换成sp
     */
    @JvmStatic
    fun px2sp(pxValue: Float): Int {
        val fontScale = context!!.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 获取屏幕的宽
     *
     * @return 屏幕的宽
     */
    @JvmStatic
    val screenWidth: Int
        get() {
            val point = screenPoint
            return point.x
        }

    /**
     * 获取屏幕的高
     *
     * @return 屏幕的高
     */
    @JvmStatic
    val screenHeight: Int
        get() {
            val point = screenPoint
            return point.y
        }

    /**
     * 通过 [WindowManager] 获取屏幕宽高信息
     *
     * @return 包含屏幕宽和高信息的 [Point]
     */
    @JvmStatic
    private val screenPoint: Point
        get() {
            val windowManager = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val point = Point()
            windowManager.defaultDisplay.getRealSize(point)
            return point
        }

    /**
     * 设置屏幕透明度
     *
     * @param bgAlpha 最终透明度 0.0~1.0
     */
    @JvmStatic
    fun backgroundAlpha(activity: Activity, bgAlpha: Float) {
        val lp = activity.window.attributes
        lp.alpha = bgAlpha
        activity.window.attributes = lp
    }

    /**
     * 获取主线程的[Handler]
     *
     * @return 主线程的Handler
     */
    @JvmStatic
    val handler: Handler
        get() = Handler(Looper.getMainLooper())

    /**
     * 获取主线程的 [Looper]
     *
     * @return 主线程的 Looper
     */
    @JvmStatic
    val mainLooper: Looper
        get() = Looper.getMainLooper()

    /**
     * 获取主线对象
     *
     * @return 主线程 Thread
     */
    @JvmStatic
    val mainThread: Thread
        get() = mainLooper.thread

    /**
     * 判断当前的线程是不是在主线程
     *
     * @return true：是主线程
     */
    @JvmStatic
    val isRunInMainThread: Boolean
        get() = Thread.currentThread().id == mainThread.id

    /**
     * 运行在新的线程中
     *
     * @param runnable 需要运行在新线程的 [Runnable]
     */
    @JvmStatic
    fun runOnNewThread(runnable: Runnable?) {
        val thread = Thread(runnable)
        thread.start()
    }

    /**
     * 延时在主线程执行[Runnable]
     *
     * @param runnable    需要执行的 [Runnable]
     * @param delayMillis 延迟时间
     * @return 是否执行成功 true：成功
     */
    @JvmStatic
    fun postDelayed(runnable: Runnable?, delayMillis: Long): Boolean {
        return handler.postDelayed(runnable!!, delayMillis)
    }

    /**
     * 在主线程执行[Runnable]
     *
     * @param runnable 需要执行的 [Runnable]
     * @return 是否执行成功 true：成功
     */
    @JvmStatic
    fun post(runnable: Runnable?): Boolean {
        return handler.post(runnable!!)
    }

    /**
     * 从主线程looper里面移除[Runnable]
     *
     * @param runnable 需要移出的 [Runnable]
     */
    @JvmStatic
    fun removeCallbacks(runnable: Runnable?) {
        handler.removeCallbacks(runnable!!)
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     *
     * @param resId 显示信息的资源id
     */
    @JvmStatic
    fun showToast(@StringRes resId: Int) {
        showToast(context!!.resources.getString(resId))
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     *
     * @param str 现实的信息
     */
    @JvmStatic
    fun showToast(str: String) {
        if (isRunInMainThread) {
            realShowToast(str)
        } else {
            post { realShowToast(str) }
        }
    }

    private var mToast: Toast? = null

    /**
     * 显示单例Toast
     *
     * @param str
     */
    @JvmStatic
    private fun realShowToast(str: String) {
        if (null != context) {
            if (null == mToast) {
                mToast = Toast.makeText(context, str, Toast.LENGTH_SHORT)
                mToast?.setGravity(Gravity.CENTER, 0, 0)
            }
            mToast!!.setText(str)
            mToast!!.show()
        }
    }
}