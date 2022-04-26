package com.renj.fragment.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.renj.fragment.utils.SystemBarUtils;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-10-13   16:34
 * <p>
 * 描述：Activity基类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BaseActivity extends AppCompatActivity {

    public BaseActivity() {
        super();
        structureMethod();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInitViewData();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        setContentView(getLayoutId());

        SystemBarUtils.setStatusWhiteAndDark(this);

        initView();
        initListener();
        initData(getIntent());
    }

    protected void beforeInitViewData() {
    }

    /**
     * 构造方法中调用，在 onCreate() 方法之前执行
     */
    protected void structureMethod() {

    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData(@Nullable Intent intent);

    protected void initListener() {
    }
}
