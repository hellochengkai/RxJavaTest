package com.hellochengkai.github.test;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;


public class CompletableTest implements Runnable {
    @Override
    public void run() {

        CompletableObserver completableObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("CompletableObserver.onSubscribe");
            }

            @Override
            public void onComplete() {
                System.out.println("CompletableObserver.onComplete");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("CompletableObserver.onError");
            }
        };
        /**
         * Completable 不发射数据，只处理 onComplete 和 onError
         */
        Completable.create(emitter -> emitter.onComplete())
                .subscribe(completableObserver);

        //fromAction会自动调用 onComplete
        Completable.fromAction(() -> System.out.println("CompletableTest.run 33"))
                .subscribe(completableObserver);


        Completable.create(emitter -> {
//                emitter.onComplete();
            System.out.println("CompletableTest.run 33");
        }).subscribe(completableObserver);

//      completable.compose(upstream -> cs -> upstream.subscribe(cs));

        /**
         * Completable 经常结合andThen进行下一步操作，andThen 只有在调用了onComplete 才会被触发
         */
//
        Completable.create(emitter -> {
            System.out.println("CompletableTest.subscribe1 " + System.currentTimeMillis());
            emitter.onComplete();//只有调用了onComplete之后才会触发之后的andThen
        })
                .andThen((CompletableSource) cs -> {
                    System.out.println("CompletableTest.subscribe2 " + System.currentTimeMillis());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cs.onComplete();
                })
                .andThen(Observable.range(1, 10))
                .subscribe(integer -> System.out.println("CompletableTest.accept " + integer + "   " + System.currentTimeMillis()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
