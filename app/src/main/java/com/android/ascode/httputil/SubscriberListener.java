package com.android.ascode.httputil;

/**
 * Created by yanfeng on 2017/5/23.
 */

public interface SubscriberListener<T> {
    void onNext(T t, int httpcode);
}
