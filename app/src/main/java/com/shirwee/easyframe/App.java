package com.shirwee.easyframe;

import android.app.Application;

import com.shirwee.easyframe.utils.UiUtils;

/**
 * @author shirwee
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UiUtils.init(this);
    }
}
