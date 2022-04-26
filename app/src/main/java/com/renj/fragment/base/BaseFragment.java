package com.renj.fragment.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-10-13   16:34
 * <p>
 * 描述：Fragment 基类，不是懒加载的 Fragment，如果需要使用懒加载请使用 {@link LazyFragment}
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BaseFragment extends Fragment {

    public BaseFragment() {
        super();
        structureMethod();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        beforeInitViewData();

        View rootView = inflater.inflate(getLayoutId(), container, false);

        initView(rootView);
        initListener(rootView);
        initData(rootView, getArguments());
        return rootView;
    }

    /**
     * 构造方法中调用，在 onCreate() 方法之前执行
     */
    protected void structureMethod() {
    }

    /**
     * 在初始化View和Data之前调用
     */
    protected void beforeInitViewData() {
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initView(View rootView);

    protected void initListener(View rootView) {
    }

    /**
     * 初始化数据，<b>注意：该方法并非延迟执行方法。延迟方法：{@link LazyFragment#userFirstVisible()}</b><br/>
     *
     * @param rootView  跟布局
     * @param arguments 参数
     * @see LazyFragment#userFirstInVisible()
     * @see LazyFragment#userVisible()
     * @see LazyFragment#userFirstInVisible()
     * @see LazyFragment#userInVisible()
     */
    protected abstract void initData(View rootView, @Nullable Bundle arguments);
}
