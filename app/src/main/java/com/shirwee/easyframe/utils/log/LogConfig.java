package com.shirwee.easyframe.utils.log;


import android.text.TextUtils;


public class LogConfig {

    private boolean showThreadInfo = false;
    private boolean debug          = true;
    private String  tag            = "EasyFrame";


    public LogConfig setTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            this.tag = tag;
        }
        return this;
    }

    public LogConfig setShowThreadInfo(boolean showThreadInfo) {
        this.showThreadInfo = showThreadInfo;
        return this;
    }


    public LogConfig setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }
}
