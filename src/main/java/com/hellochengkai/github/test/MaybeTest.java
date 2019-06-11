package com.hellochengkai.github.test;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
public class MaybeTest implements Runnable{
    @Override
    public void run() {
        /**
         * Maybe 发射0个或者1个数据。要么成功，要么失败
         */
        Maybe.just("aaa").subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                System.out.println("MaybeTest.onSuccess " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
