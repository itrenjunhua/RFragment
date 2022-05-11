package com.renj.fragment

import android.content.Intent
import android.widget.TextView
import com.renj.fragment.add.AddFragmentActivity
import com.renj.fragment.base.BaseActivity
import com.renj.fragment.replace.DefaultReplaceActivity
import com.renj.fragment.replace.ReplaceFragmentActivity
import com.renj.fragment.vp.VpFragmentActivity

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

        findViewById<TextView>(R.id.tv_replace_fragment).setOnClickListener {
            ReplaceFragmentActivity.launch(this)
        }
        findViewById<TextView>(R.id.tv_replace_def_fragment).setOnClickListener {
            DefaultReplaceActivity.launch(this)
        }

        findViewById<TextView>(R.id.tv_vp_fragment).setOnClickListener {
            VpFragmentActivity.launch(this)
        }
    }
}