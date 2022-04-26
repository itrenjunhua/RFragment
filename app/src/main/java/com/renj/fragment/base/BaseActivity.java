package com.renj.fragment.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.renj.fragment.R;
import com.renj.fragment.utils.ResUtils;
import com.renj.fragment.utils.SystemBarUtils;
import com.renj.fragment.widget.TitleView;

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
    protected TitleView titleView;

    public BaseActivity() {
        super();
        structureMethod();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInitViewData();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        setContentView(R.layout.base_activity);
        titleView = findViewById(R.id.page_title);
        FrameLayout pageContent = findViewById(R.id.page_content);
        LayoutInflater.from(this).inflate(getLayoutId(), pageContent, true);

        SystemBarUtils.setStatusWhiteAndDark(this);

        initView();
        initListener();
        initBack();
        initData(getIntent());
    }

    private void initBack() {
        // 顶部返回按钮
        titleView.setOnBackViewClickListener(this::handlerOnBack);
        // 物理返回按钮
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                handlerOnBack();
            }
        });
    }

    protected void handlerOnBack() {
        finish();
    }

    protected void beforeInitViewData() {
    }

    /**
     * 构造方法中调用，在 onCreate() 方法之前执行
     */
    protected void structureMethod() {

    }

    public void setTitle(@StringRes int titleId) {
        setTitle(ResUtils.getString(titleId));
    }

    public void setTitle(String title) {
        titleView.setTitleContent(title);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData(@Nullable Intent intent);

    protected void initListener() {
    }
}
