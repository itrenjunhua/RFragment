package com.renj.fragment.base;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-10-14   10:06
 * <p>
 * 描述：当有多重嵌套时(比如 ViewPager 嵌套 ViewPager，再嵌套 Fragment)，
 * 宿主Fragment在生命周期执行的时候会相应的分发到子Fragment中，但是setUserVisibleHint()和onHiddenChanged()却没有进行相应的回调，
 * 如果子Fragment需要监听父Fragment的显示、隐藏状态时子Fragment通过实现该接口重写对应方法即可
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IParentVisibilityObserver {
    /**
     * 父Fragment 用户 <b>第一次可见，用于延迟加载数据</b>。非第一次可见时不会执行该方法，只会执行 {@link #userParentVisible()}
     */
    void userParentFirstVisible();

    /**
     * 父Fragment 用户<b>非第一次可见</b>。第一次可见时不会执行该方法，只会执行 {@link #userParentFirstVisible()}
     */
    void userParentVisible();

    /**
     * 父Fragment 用户<b>第一次不可见</b>。非第一次不可见时不会执行该方法，只会执行 {@link #userParentInVisible()}
     */
    void userParentFirstInVisible();

    /**
     * 父Fragment 用户<b>非第一次不可见</b>。第一次不可见时不会执行该方法，只会执行 {@link #userParentFirstInVisible()}
     */
    void userParentInVisible();
}
