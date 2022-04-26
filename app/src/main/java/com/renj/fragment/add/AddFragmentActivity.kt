package com.renj.fragment.add

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
 * 描述：Add Fragment 形式到页面
 *
 * 修订历史：
 *
 * ======================================================================
 */
class AddFragmentActivity : BaseActivity() {
    private lateinit var tvFragment1: TextView
    private lateinit var tvFragment2: TextView

    private lateinit var addFragment1: AddFragment1
    private lateinit var addFragment2: AddFragment2

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, AddFragmentActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.add_fragment_activity
    }

    override fun initView() {
        tvFragment1 = findViewById<TextView>(R.id.tv_fragment1)
        tvFragment2 = findViewById<TextView>(R.id.tv_fragment2)

        addFragment1 = AddFragment1.newInstance()
        addFragment2 = AddFragment2.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.fl_content, addFragment1)
            .add(R.id.fl_content, addFragment2)
            .commitAllowingStateLoss();
        changeShowFragment(addFragment1, addFragment2)
    }

    override fun initData(intent: Intent?) {
        setTitle(R.string.add_fragment)
    }

    override fun initListener() {
        tvFragment1.setOnClickListener { changeShowFragment(addFragment1, addFragment2) }
        tvFragment2.setOnClickListener { changeShowFragment(addFragment2, addFragment1) }
    }

    private fun changeShowFragment(showFragment: Fragment, hideFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .show(showFragment)
            .hide(hideFragment)
            .commitAllowingStateLoss()
    }
}