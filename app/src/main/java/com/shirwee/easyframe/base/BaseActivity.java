package com.shirwee.easyframe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shirwee.easyframe.manage.ActivityStack;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author shirwee
 */
public abstract class BaseActivity extends RxAppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(this);
        setContentView(getLayoutId());
        init(savedInstanceState);
    }

    //    protected void showToast(String msg) {
    //        ToastUtils.show(msg);
    //    }

    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getInstance().finishActivity();
    }
}
