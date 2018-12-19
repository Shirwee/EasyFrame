package com.shirwee.easyframe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shirwee.easyframe.manage.ActivityStack;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author shirwee
 */
public abstract class BaseActivity extends RxAppCompatActivity{

    public CompositeDisposable mCompositeDisposable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(this);
        setContentView(getLayoutId());
        init(savedInstanceState);
    }


    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    /**
     * 添加订阅
     */
    public void addDisposable(Disposable... ds) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.addAll(ds);
    }

    /**
     * 取消所有订阅
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getInstance().removeActivity(this);
    }
}
