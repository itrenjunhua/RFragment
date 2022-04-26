package com.renj.fragment

import android.content.Intent
import android.widget.TextView
import com.renj.fragment.add.AddFragmentActivity
import com.renj.fragment.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        titleView.setBackViewShow(false)
    }

    override fun initData(intent: Intent?) {
        setTitle(R.string.main_title)
    }

    override fun initListener() {
        findViewById<TextView>(R.id.tv_add_fragment).setOnClickListener {
            AddFragmentActivity.launch(this)
        }
    }
}