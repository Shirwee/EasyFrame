package com.shirwee.easyframe;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.shirwee.easyframe.base.BaseActivity;
import com.shirwee.easyframe.base.BasicResponse;
import com.shirwee.easyframe.bean.MeiZi;
import com.shirwee.easyframe.manage.FragmentFactory;
import com.shirwee.easyframe.net.DefaultObserver;
import com.shirwee.easyframe.net.IdeaApi;
import com.shirwee.easyframe.utils.ToastUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity
        extends BaseActivity {

    private RadioGroup radioGroup;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        radioGroup = findViewById(R.id.radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchFragment(checkedId);
            }
        });

        switchFragment(R.id.rb_home);
    }

    protected void switchFragment(int checkedId) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fl_container, FragmentFactory.getInstance()
                        .getMainFragment(checkedId))
                .commit();
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
                        ToastUtils.normal("请求成功，妹子个数为" + results.size());
                    }

                    @Override
                    public void onFail(Throwable e) {
                        super.onFail(e);
                    }
                });
    }
}
