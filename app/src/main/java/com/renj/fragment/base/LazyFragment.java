package com.renj.fragment.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.renj.fragment.utils.Logger;

import java.util.List;


/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-10-13   11:06
 * <p>
 * 描述：懒加载 Fragment，兼容 add()、replace()、ViewPager中使用形式
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class LazyFragment extends BaseFragment {
    private static final int FRAGMENT_STATUS_UN_INIT = -1;
    private static final int FRAGMENT_STATUS_INIT_FINISH = 0;
    private static final int FRAGMENT_STATUS_FIRST_VISIBLE = 1;
    private static final int FRAGMENT_STATUS_VISIBLE = 2;
    private static final int FRAGMENT_STATUS_FIRST_INVISIBLE = 3;
    private static final int FRAGMENT_STATUS_INVISIBLE = 4;

    // -1：初始化未完成 0：初始化完成 1：第一次对用户可见 2：第一次对用户不可见 3：对用户可见  4：对用户不可见
    private int currentFragment = FRAGMENT_STATUS_UN_INIT;
    // onHiddenChanged() 或者 setUserVisibleHint() 方法当前状态是否为对用户可见状态
    private boolean hiddenAndVisibleStatusVisible = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.i(this.getClass().getSimpleName() + " onCreateView ============= ");
        currentFragment = FRAGMENT_STATUS_UN_INIT;
        View view = super.onCreateView(inflater, container, savedInstanceState);
        currentFragment = FRAGMENT_STATUS_INIT_FINISH;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.i(this.getClass().getSimpleName() + " onActivityCreated ============= ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.i(this.getClass().getSimpleName() + " onResume ============= " + currentFragment + " -- " + hiddenAndVisibleStatusVisible);

        if (hiddenAndVisibleStatusVisible) {
            if (currentFragment == FRAGMENT_STATUS_INIT_FINISH) {
                userFirstVisible();
                notifyChildHiddenChange(FRAGMENT_STATUS_FIRST_VISIBLE);
                currentFragment = FRAGMENT_STATUS_FIRST_VISIBLE;
            } else if (currentFragment == FRAGMENT_STATUS_FIRST_INVISIBLE
                    || currentFragment == FRAGMENT_STATUS_INVISIBLE
                    || currentFragment == FRAGMENT_STATUS_VISIBLE) {
                userVisible();
                notifyChildHiddenChange(FRAGMENT_STATUS_VISIBLE);
                currentFragment = FRAGMENT_STATUS_VISIBLE;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.i(this.getClass().getSimpleName() + " onPause ============= " + currentFragment + " -- " + hiddenAndVisibleStatusVisible);

        if (hiddenAndVisibleStatusVisible) {
            if (currentFragment == FRAGMENT_STATUS_FIRST_VISIBLE) {
                userFirstInVisible();
                notifyChildHiddenChange(FRAGMENT_STATUS_FIRST_INVISIBLE);
                currentFragment = FRAGMENT_STATUS_INVISIBLE;
            } else if (currentFragment == FRAGMENT_STATUS_VISIBLE
                    || currentFragment == FRAGMENT_STATUS_INVISIBLE) {
                userInVisible();
                notifyChildHiddenChange(FRAGMENT_STATUS_INVISIBLE);
                currentFragment = FRAGMENT_STATUS_INVISIBLE;
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        hiddenAndVisibleStatusVisible = !hidden;

        if (!hidden) {
            if (currentFragment == FRAGMENT_STATUS_INIT_FINISH) {
                userFirstVisible();
                notifyChildHiddenChange(FRAGMENT_STATUS_FIRST_VISIBLE);
                currentFragment = FRAGMENT_STATUS_FIRST_VISIBLE;
            } else if (currentFragment == FRAGMENT_STATUS_FIRST_INVISIBLE
                    || currentFragment == FRAGMENT_STATUS_INVISIBLE) {
                userVisible();
                notifyChildHiddenChange(FRAGMENT_STATUS_VISIBLE);
                currentFragment = FRAGMENT_STATUS_VISIBLE;
            }
        } else {
            if (currentFragment == FRAGMENT_STATUS_FIRST_VISIBLE) {
                userFirstInVisible();
                notifyChildHiddenChange(FRAGMENT_STATUS_FIRST_INVISIBLE);
                currentFragment = FRAGMENT_STATUS_FIRST_INVISIBLE;
            } else if (currentFragment == FRAGMENT_STATUS_VISIBLE) {
                userInVisible();
                notifyChildHiddenChange(FRAGMENT_STATUS_INVISIBLE);
                currentFragment = FRAGMENT_STATUS_INVISIBLE;
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        hiddenAndVisibleStatusVisible = isVisibleToUser;
        Logger.i(this.getClass().getSimpleName() + " setUserVisibleHint ============= isVisibleToUser: " + isVisibleToUser);

        if (isVisibleToUser) {
            if (currentFragment == FRAGMENT_STATUS_INIT_FINISH) {
                userFirstVisible();
                notifyChildHiddenChange(FRAGMENT_STATUS_FIRST_VISIBLE);
                currentFragment = FRAGMENT_STATUS_FIRST_VISIBLE;
            } else if (currentFragment == FRAGMENT_STATUS_FIRST_INVISIBLE
                    || currentFragment == FRAGMENT_STATUS_INVISIBLE) {
                userVisible();
                notifyChildHiddenChange(FRAGMENT_STATUS_VISIBLE);
                currentFragment = FRAGMENT_STATUS_VISIBLE;
            }
        } else {
            if (currentFragment == FRAGMENT_STATUS_FIRST_VISIBLE) {
                userFirstInVisible();
                notifyChildHiddenChange(FRAGMENT_STATUS_FIRST_INVISIBLE);
                currentFragment = FRAGMENT_STATUS_FIRST_INVISIBLE;
            } else if (currentFragment == FRAGMENT_STATUS_VISIBLE) {
                userInVisible();
                notifyChildHiddenChange(FRAGMENT_STATUS_INVISIBLE);
                currentFragment = FRAGMENT_STATUS_INVISIBLE;
            }
        }
    }

    /**
     * 用户 <b>第一次可见，用于延迟加载数据</b>。非第一次可见时不会执行该方法，只会执行 {@link #userVisible()}
     */
    protected void userFirstVisible() {
        Logger.i(this.getClass().getSimpleName() + " fistVisible ============= 用户第一次可见");
    }

    /**
     * 用户<b>非第一次可见</b>。第一次可见时不会执行该方法，只会执行 {@link #userFirstVisible()}
     */
    protected void userVisible() {
        Logger.i(this.getClass().getSimpleName() + " visible ============= 用户可见");
    }

    /**
     * 用户<b>第一次不可见</b>。非第一次不可见时不会执行该方法，只会执行 {@link #userInVisible()}
     */
    protected void userFirstInVisible() {
        Logger.i(this.getClass().getSimpleName() + " fistInVisible ============= 用户第一次不可见");
    }

    /**
     * 用户<b>非第一次不可见</b>。第一次不可见时不会执行该方法，只会执行 {@link #userFirstInVisible()}
     */
    protected void userInVisible() {
        Logger.i(this.getClass().getSimpleName() + " inVisible ============= 用户不可见");
    }

    /**
     * 当自己的显示隐藏状态改变时，调用这个方法通知子Fragment
     *
     * @param fragmentStatus 状态
     */
    private void notifyChildHiddenChange(int fragmentStatus) {
        if (isDetached() || !isAdded()) {
            return;
        }
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment fragment : fragments) {
            if (fragment instanceof IParentVisibilityObserver) {
                IParentVisibilityObserver iParentVisibilityObserver = (IParentVisibilityObserver) fragment;
                if (fragmentStatus == FRAGMENT_STATUS_FIRST_VISIBLE) {
                    iParentVisibilityObserver.userParentFirstVisible();
                } else if (fragmentStatus == FRAGMENT_STATUS_VISIBLE) {
                    iParentVisibilityObserver.userParentVisible();
                } else if (fragmentStatus == FRAGMENT_STATUS_FIRST_INVISIBLE) {
                    iParentVisibilityObserver.userParentFirstInVisible();
                } else if (fragmentStatus == FRAGMENT_STATUS_INVISIBLE) {
                    iParentVisibilityObserver.userParentInVisible();
                }
            }
        }
    }
}
