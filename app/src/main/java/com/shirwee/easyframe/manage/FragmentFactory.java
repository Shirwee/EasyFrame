package com.shirwee.easyframe.manage;

import android.app.Fragment;

import com.shirwee.easyframe.R;
import com.shirwee.easyframe.ui.ExerciseFragment;
import com.shirwee.easyframe.ui.HomeFragment;
import com.shirwee.easyframe.ui.MeFragment;
import com.shirwee.easyframe.ui.VideoFragment;

import java.util.HashMap;

/*
 *  描述：Fragment工厂，用来存放fragment的实例
 *  @author shirwee
 */
public class FragmentFactory {
    private static HashMap<Integer,Fragment> mCaches = new HashMap<>();

    // 单例设计模式
    private static FragmentFactory sInstance;

    private FragmentFactory(){}

    public static FragmentFactory getInstance() {
        if(sInstance == null) {
            sInstance = new FragmentFactory();
        }

        return sInstance;
    }

    public Fragment getMainFragment(int checkId) {
        Fragment fragment = null;
        // 如果有缓存，直接拿缓存
        if(mCaches.containsKey(checkId)) {
            fragment = mCaches.get(checkId);
            return fragment;
        }
        switch (checkId) {
            case R.id.rb_home:
                fragment = new HomeFragment();
                break;
            case R.id.rb_video:
                fragment = new VideoFragment();
                break;
            case R.id.rb_exercise:
                fragment = new ExerciseFragment();
                break;
            case R.id.rb_me:
                fragment = new MeFragment();
                break;

            default:
                break;
        }
        // 把fragment添加到缓存中
        mCaches.put(checkId,fragment);
        return fragment;
    }


}
