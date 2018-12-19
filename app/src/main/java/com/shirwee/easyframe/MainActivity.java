package com.shirwee.easyframe;

import android.os.Bundle;
import android.view.View;

import com.shirwee.easyframe.base.BaseActivity;
import com.shirwee.easyframe.base.BasicResponse;
import com.shirwee.easyframe.bean.MeiZi;
import com.shirwee.easyframe.net.DefaultObserver;
import com.shirwee.easyframe.net.IdeaApi;
import com.shirwee.easyframe.utils.ToastUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity
        extends BaseActivity
{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        IdeaApi.getApiService()
               .getMezi()
               .compose(this.<BasicResponse<List<MeiZi>>>bindToLifecycle())
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new DefaultObserver<BasicResponse<List<MeiZi>>>(this, true) {
                   @Override
                   public void onSuccess(BasicResponse<List<MeiZi>> response) {
                       List<MeiZi> results = response.getResults();
                       ToastUtils.normal("请求成功，妹子个数为"+results.size());
                   }

                   @Override
                   public void onFail(Throwable e) {
                       super.onFail(e);
                   }
               });
    }
}
