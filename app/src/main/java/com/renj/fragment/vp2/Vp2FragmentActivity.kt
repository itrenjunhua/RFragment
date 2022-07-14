package com.renj.fragment.vp2

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.renj.fragment.R
import com.renj.fragment.base.BaseActivity
import com.renj.fragment.utils.Logger
import com.renj.fragment.utils.ResUtils

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2022-05-06   17:23
 *
 * 描述：ViewPager 中使用 Fragment
 *
 * 修订历史：
 *
 * ======================================================================
 */
class Vp2FragmentActivity : BaseActivity(), FragmentDataChangeListener {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private var fragments = listOf(
        Vp2Fragment.newInstance(
            ResUtils.getString(R.string.vp2_tab1),
            ResUtils.getColor(R.color.color_content_bg_one)
        ),
        Vp2Fragment.newInstance(
            ResUtils.getString(R.string.vp2_tab2),
            ResUtils.getColor(R.color.color_content_bg_two)
        ),
        Vp2Fragment.newInstance(
            ResUtils.getString(R.string.vp2_tab3),
            ResUtils.getColor(R.color.color_content_bg_three)
        ),
        Vp2Fragment.newInstance(
            ResUtils.getString(R.string.vp2_tab4),
            ResUtils.getColor(R.color.color_content_bg_four)
        )
    )

    private var titles = listOf(
        ResUtils.getString(R.string.vp_title1),
        ResUtils.getString(R.string.vp_title2),
        ResUtils.getString(R.string.vp_title3),
        ResUtils.getString(R.string.vp_title4),
    )

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, Vp2FragmentActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.vp2_fragment_activity
    }

    override fun initView() {
        setTitle(R.string.vp2_fragment)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
    }

    override fun initData(intent: Intent?) {
        tabLayout.apply {
            addTab(newTab().setText(titles[0]))
            addTab(newTab().setText(titles[1]))
            addTab(newTab().setText(titles[2]))
            addTab(newTab().setText(titles[3]))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.select()
                    viewPager.currentItem = tab?.position ?: 0
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
        viewPager.apply {
            adapter = Vp2FragmentAdapter(this@Vp2FragmentActivity, fragments)

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tabLayout.getTabAt(position)?.select()
                }
            })

            currentItem = 0
        }
    }

    class Vp2FragmentAdapter(
        activity: FragmentActivity,
        private val fragments: List<Vp2Fragment>
    ) :
        FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

    }

    override fun onDataChange(message: String) {
        Logger.i("FragmentDataChangeListener: $message")
    }
}