package com.shirwee.easyframe.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

/**
 * 初始化相关
 * @author shirwee
 */
public class UiUtils {

    private static Context context;
    /**
     * 做成全局静态的
     */
    private static Handler handler;

    private UiUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        UiUtils.context = context.getApplicationContext();
        UiUtils.handler = new Handler(){};
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("u should init first");
    }

    public static Handler getHandler() {
        if (handler != null) {
            return handler;
        }
        throw new NullPointerException("u should init first");
    }

    // 开始执行一个任务
    public static void post(Runnable runnable) {
        handler.post(runnable);
    }

    // 延迟执行一个任务
    public static void postDelay(Runnable task, long delay) {
        handler.postDelayed(task, delay);
    }

    // 移除一个任务
    public static void remove(Runnable task) {
        handler.removeCallbacks(task);
    }

    /**
     * 得到resources对象
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到string.xml中的字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 得到string.xml中的字符串，带点位符
     */
    public static String getString(int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    /**
     * 得到string.xml中和字符串数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArr(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到colors.xml中的颜色
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

}