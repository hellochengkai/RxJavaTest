package com.hellochengkai.github;

import io.reactivex.FlowableSubscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.locks.LockSupport;

public class MyFlowableSubscriber<T> implements FlowableSubscriber<T> {

    static final String TAG = MyFlowableSubscriber.class.getSimpleName();

    public static MyFlowableSubscriber create(String name) {
        return new MyFlowableSubscriber(name);
    }

    private String name;

    private MyFlowableSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(18); //观察者设置接收事件的数量,如果不设置接收不到事件
        System.out.println("\n" + name + " : " + TAG + ".onSubscribe at " + Thread.currentThread().getName());
    }

    @Override
    public void onNext(T t) {
        System.out.println(name + " : " + TAG + ".onNext(" + t + ") at " + Thread.currentThread().getName());
    }

    @Override
    public void onError(Throwable t) {
        System.out.println(name + " : " + TAG + ".onError " + t.getMessage() + " at " + Thread.currentThread().getName() + "\n");
    }

    @Override
    public void onComplete() {
        System.out.println(name + " : " + TAG + ".onComplete at " + Thread.currentThread().getName() + "\n");
    }
}
