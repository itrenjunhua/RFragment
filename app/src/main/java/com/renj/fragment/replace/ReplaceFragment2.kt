package com.renj.fragment.replace

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.renj.fragment.R
import com.renj.fragment.base.LazyFragment
import com.renj.fragment.utils.ResUtils

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2022-04-26   15:57
 *
 * 描述：[ReplaceFragmentActivity] 中的 [androidx.fragment.app.Fragment]
 *
 * 修订历史：
 *
 * ======================================================================
 */
class ReplaceFragment2 : LazyFragment() {
    private lateinit var llContent: LinearLayout
    private lateinit var tvContent: TextView

    companion object {
        fun newInstance(): ReplaceFragment2 {
            return ReplaceFragment2()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.add_fragment
    }

    override fun initView(rootView: View?) {
        rootView?.apply {
            llContent = findViewById(R.id.ll_content)
            tvContent = findViewById(R.id.tv_content)
        }
    }

    override fun initData(rootView: View?, arguments: Bundle?) {
        llContent.setBackgroundColor(ResUtils.getColor(R.color.color_content_bg_four))
        tvContent.text = ResUtils.getString(R.string.replace_fragment2)
    }
}