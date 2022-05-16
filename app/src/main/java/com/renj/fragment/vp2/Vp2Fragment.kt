package com.renj.fragment.vp2

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
 * 创建时间：2022-05-06   17:49
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
class Vp2Fragment : LazyFragment() {
    private lateinit var llContent: LinearLayout
    private lateinit var tvContent: TextView

    companion object {
        fun newInstance(content: String, color: Int): Vp2Fragment {
            val arguments = Bundle()
            arguments.putString("content", content)
            arguments.putInt("color", color)
            arguments.putString("tag", content)
            val vpFragment = Vp2Fragment()
            vpFragment.arguments = arguments
            return vpFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.replace_fragment
    }

    override fun initView(rootView: View?) {
        rootView?.apply {
            llContent = findViewById(R.id.ll_content)
            tvContent = findViewById(R.id.tv_content)
        }
    }

    override fun initData(rootView: View?, arguments: Bundle?) {
        arguments?.apply {
            llContent.setBackgroundColor(
                getInt(
                    "color",
                    ResUtils.getColor(R.color.color_content_bg_one)
                )
            )
            tvContent.text = getString("content", "")
        }
    }
}