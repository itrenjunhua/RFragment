package com.renj.fragment.replace

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.renj.fragment.R
import com.renj.fragment.base.BaseActivity

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2022-04-26   15:18
 *
 * 描述：Replace Fragment 形式到页面
 *
 * 修订历史：
 *
 * ======================================================================
 */
class ReplaceFragmentActivity : BaseActivity() {
    private lateinit var tvFragment1: TextView
    private lateinit var tvFragment2: TextView

    private lateinit var replaceFragment1: ReplaceFragment1
    private lateinit var replaceFragment2: ReplaceFragment2

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, ReplaceFragmentActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.replace_fragment_activity
    }

    override fun initView() {
        tvFragment1 = findViewById(R.id.tv_fragment1)
        tvFragment2 = findViewById(R.id.tv_fragment2)

        replaceFragment1 = ReplaceFragment1.newInstance()
        replaceFragment2 = ReplaceFragment2.newInstance()

        changeShowFragment(replaceFragment1)
    }

    override fun initData(intent: Intent?) {
        setTitle(R.string.replace_fragment)
    }

    override fun initListener() {
        tvFragment1.setOnClickListener { changeShowFragment(replaceFragment1) }
        tvFragment2.setOnClickListener { changeShowFragment(replaceFragment2) }
    }

    private fun changeShowFragment(showFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, showFragment)
            .commitAllowingStateLoss()
    }
}