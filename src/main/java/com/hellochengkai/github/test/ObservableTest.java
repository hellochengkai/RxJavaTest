package com.hellochengkai.github.test;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.NewThreadScheduler;
import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.ThreadFactory;
import java.util.function.Function;

public class ObservableTest implements Function {

    @Override
    public Object apply(Object o) {
        /**
         * Observable 发射0-n个事件，并以错误或者成功结束
         *
         * Observable 的 just、creat、range、fromXXX 等操作符都能生成Cold Observable。
         * cold Observable 和 hot Observable，区别
         * hot闭关是否被订阅都会触发事件，
         *  有多个订阅者时，Observable和订阅者们是一对多的，订阅者收到的时间都是同一个事件，类似广播
         * cold只有在被订阅时才会触发事件
         *  有多个订阅者时，Observable和订阅者们是一对一的，背个订阅这收到的都是一个新的事件
         */

//        Observable.just("asdfasd","asdfasd").subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                System.out.println("ObservableTest.onSubscribe" + d.getClass().getName());
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println("ObservableTest.onNext" + "s = " + s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("ObservableTest.onError " + e);
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("ObservableTest.onComplete");
//            }
//        });


        Observable.just("sfdasd")
//                .subscribeOn(Schedulers.computation())
//                .subscribeOn(new NewThreadScheduler(new RxThreadFactory("chengkai subscribe Thread")))
//                .observeOn(new NewThreadScheduler(new RxThreadFactory("chengkai observe Thread")))
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(new NewThreadScheduler(new ThreadFactory() {
//                    @Override
//                    public Thread newThread(Runnable runnable) {
//                        Thread thread = new Thread(runnable,"test");
//                        thread.setPriority( Thread.NORM_PRIORITY);
//                        thread.setDaemon(true);
//                        return thread;
//                    }
//                }))
                .compose(new ObservableTransformer<String, String>() {
                    @Override
                    public ObservableSource<String> apply(Observable<String> upstream) {
                        return null;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("ObservableTest.onSubscribe " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("ObservableTest.onNext " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {sys
                        System.out.println("ObservableTest.onError "  + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("ObservableTest.onComplete " + Thread.currentThread().getName());
                    }
                });
        return null;
    }
}
