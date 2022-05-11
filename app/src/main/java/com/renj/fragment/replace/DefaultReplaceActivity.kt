package com.renj.fragment.replace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.renj.fragment.R
import com.renj.fragment.base.BaseActivity
import com.renj.fragment.utils.Logger

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2022-05-10   15:49
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
class DefaultReplaceActivity : BaseActivity() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, DefaultReplaceActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.replace_fragment_default_activity
    }

    override fun initView() {

    }

    override fun initData(intent: Intent?) {
        setTitle(R.string.replace_def_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i("DefaultReplaceActivity ========== onCreate 1111")
        super.onCreate(savedInstanceState)
        Logger.i("DefaultReplaceActivity ========== onCreate 44444")
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        Logger.i("DefaultReplaceActivity ========== onCreateView  1")
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        Logger.i("DefaultReplaceActivity ========== onCreateView 2")
        return super.onCreateView(parent, name, context, attrs)
    }

    override fun onStart() {
        super.onStart()
        Logger.i("DefaultReplaceActivity ========== onStart")
    }
}