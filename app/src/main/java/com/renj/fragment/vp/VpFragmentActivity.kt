package com.renj.fragment.vp

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.renj.fragment.R
import com.renj.fragment.base.BaseActivity
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
class VpFragmentActivity : BaseActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    private val listenerList = ArrayList<ActivityDataChangeListener>()

    private var fragments = listOf(
        VpFragment.newInstance(
            ResUtils.getString(R.string.vp_tab1),
            ResUtils.getColor(R.color.color_content_bg_one)
        ),
        VpFragment.newInstance(
            ResUtils.getString(R.string.vp_tab2),
            ResUtils.getColor(R.color.color_content_bg_two)
        ),
        VpFragment.newInstance(
            ResUtils.getString(R.string.vp_tab3),
            ResUtils.getColor(R.color.color_content_bg_three)
        ),
        VpFragment.newInstance(
            ResUtils.getString(R.string.vp_tab4),
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
            context.startActivity(Intent(context, VpFragmentActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.vp_fragment_activity
    }

    override fun initView() {
        setTitle(R.string.vp_fragment)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
    }

    override fun initData(intent: Intent?) {
        tabLayout.apply {
            addTab(newTab().setText(titles[0]))
            addTab(newTab().setText(titles[1]))
            addTab(newTab().setText(titles[2]))
            addTab(newTab().setText(titles[3]))
        }
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = VpFragmentAdapter(supportFragmentManager, fragments, titles)
        viewPager.currentItem = 0
    }

    override fun initListener() {
        super.initListener()
        titleView.titleContentView.setOnClickListener {
            for (dataChangeListener in listenerList) {
                dataChangeListener.onDataChange("currentItem: " + viewPager.currentItem)
            }
        }
    }

    /**
     * 注册监听
     */
    open fun registerListener(activityDataChangeListener: ActivityDataChangeListener) {
        listenerList.add(activityDataChangeListener)
    }

    /**
     * 移除监听
     */
    open fun unRegisterListener(activityDataChangeListener: ActivityDataChangeListener) {
        listenerList.remove(activityDataChangeListener)
    }

    /**
     * FragmentPagerAdapter 和 FragmentStatePagerAdapter 的差别，可以从这两个类的 destroyItem() 方法中查看：
     * FragmentPagerAdapter：只是销毁了fragment的视图，但是没有移除它。具体生命周期方法过程 => onCreateView() -> onDestroyView()
     * FragmentStatePagerAdapter：移除了不用的Fragment。具体生命周期方法过程 => onAttach() -> onDetach()
     */
    class VpFragmentAdapter(
        manager: FragmentManager,
        private val fragments: List<VpFragment>,
        private val titles: List<String>
    ) :
//         FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titles[position]
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

    }
}