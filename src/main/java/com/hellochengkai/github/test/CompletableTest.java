package com.hellochengkai.github.test;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

public class CompletableTest implements Runnable {
    @Override
    public void run() {
        /**
         * Completable 不发射数据，只处理 onComplete 和 onError
         */
        Completable.create(emitter -> {
            emitter.onComplete();
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("CompletableTest.onSubscribe");
            }

            @Override
            public void onComplete() {
                System.out.println("CompletableTest.onComplete");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("CompletableTest.onError");
            }
        });
    }
}
