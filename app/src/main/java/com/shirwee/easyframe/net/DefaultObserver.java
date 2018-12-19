package com.shirwee.easyframe.net;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.shirwee.easyframe.R;
import com.shirwee.easyframe.base.BasicResponse;
import com.shirwee.easyframe.utils.ToastUtils;
import com.shirwee.easyframe.utils.UiUtils;
import com.shirwee.easyframe.utils.log.LogUtils;
import com.shirwee.easyframe.widget.LoadDialog;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by zhpan on 2017/4/18.
 */

public abstract class DefaultObserver<T extends BasicResponse> implements Observer<T> {
    private boolean isShowLoading;
    private WeakReference<Activity> mReference;

    public DefaultObserver(Activity activity) {
        this.mReference = new WeakReference<>(activity);
    }

    public DefaultObserver(Activity activity, boolean isShowLoading) {
        this.mReference = new WeakReference<>(activity);
        this.isShowLoading = isShowLoading;
        if (isShowLoading) {
            LoadDialog.show(mReference.get(), "加载中...");
        }
    }

    public DefaultObserver(Activity activity, String message) {
        this.mReference = new WeakReference<>(activity);
        this.isShowLoading = true;
        LoadDialog.show(mReference.get(), message);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        dismissProgress();
        if (!response.isError()) {
            onSuccess(response);
        } else {
            String message = TextUtils.isEmpty(response.getMessage()) ?
                    UiUtils.getString(R.string.response_return_error)
                    : response.getMessage();

            onFail(new MessageException(message));
        }
    }


    @Override
    public void onError(Throwable e) {
        LogUtils.e(e.getMessage());
        dismissProgress();
        onFail(e);
    }

    @Override
    public void onComplete() {
    }

    private void dismissProgress() {
        if (isShowLoading) {
            LoadDialog.dismiss(mReference.get());
        }
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);


    public void onFail(Throwable e) {
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        }else if (e instanceof MessageException){
            // 自定义的异常
            ToastUtils.normal(e.getMessage());
        }else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.normal(R.string.connect_error);
                break;

            case CONNECT_TIMEOUT:
                ToastUtils.normal(R.string.connect_timeout);
                break;

            case BAD_NETWORK:
                ToastUtils.normal(R.string.bad_network);
                break;

            case PARSE_ERROR:
                ToastUtils.normal(R.string.parse_error);
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtils.normal(R.string.unknown_error);
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
