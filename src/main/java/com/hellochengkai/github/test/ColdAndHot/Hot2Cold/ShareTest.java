package com.hellochengkai.github.test.ColdAndHot.Hot2Cold;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class ShareTest implements Runnable{
    @Override
    public void run() {
        //cold --> hot --> cold
        Consumer<Long> subscriber1 = aLong -> System.out.println("subscriber1: "+aLong);
        Consumer<Long> subscriber2 = aLong -> System.out.println("   subscriber2: "+aLong);
        /**
         * share 操作符封装了 publish 和 refCount，
         * connectableObservable.share();
         *
         *     @CheckReturnValue
         *     @SchedulerSupport(SchedulerSupport.NONE)
         *     public final Observable<T> share() {
         *         return publish().refCount();
         *     }
         */
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {
                Observable.interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .take(Integer.MAX_VALUE)
                        .subscribe(e::onNext);
            }
        }).observeOn(Schedulers.newThread()).share();
        Disposable disposable1 = observable.subscribe(subscriber1);
        Disposable disposable2 = observable.subscribe(subscriber2);
        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //refCount 产生的 Cold observable：
        /**
         *所有的订阅者都取消了订阅以后，被观察者的事件会重置，再有订阅者时会重新开始发送事件。
         *disposable1.dispose();
         *disposable2.dispose();
         */

        /**
         *如果不是所有的订阅者都取消了订阅，只取消了部分。部分的订阅者重新开始订阅，则不会从头开始数据流。
         * disposable1.dispose();
         * //disposable2.dispose();
         */
        disposable1.dispose();
        disposable2.dispose();

        System.out.println("重新开始数据流");

        disposable1 = observable.subscribe(subscriber1);
        disposable2 = observable.subscribe(subscriber2);

        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
