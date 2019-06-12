package com.hellochengkai.github.test.ColdAndHot.Cold2Hot;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

public class SubjectTest implements Runnable  {
    @Override
    public void run() {

        /**
         * Subject 既是 Observable 又是 Observer(Subscriber)。这一点可以从 Subject 的源码上看到。
         * 当 Subject 作为 Subscriber 时，
         * 它可以订阅目标 Cold Observable 使对方开始发送事件。
         * 同时它又作为Observable 转发或者发送新的事件，
         * 让 Cold Observable 借助 Subject 转换为 Hot Observable。
         * 注意，Subject 并不是线程安全的，如果想要其线程安全需要调用toSerialized()方法。
         */
        Consumer<Long> subscriber1 = aLong -> System.out.println("subscriber1: " + aLong);

        Consumer<Long> subscriber2 = aLong -> System.out.println("   subscriber2: " + aLong);

        Consumer<Long> subscriber3 = aLong -> System.out.println("      subscriber3: " + aLong);


        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {
                Observable.interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .take(Integer.MAX_VALUE)
                        .subscribe(e::onNext);
            }
        }).observeOn(Schedulers.newThread());

        PublishSubject publishSubject = PublishSubject.create();
        publishSubject.toSerialized();//使之线程安全
        observable.subscribe(publishSubject);

        publishSubject.subscribe(subscriber1);
        publishSubject.subscribe(subscriber2);
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publishSubject.subscribe(subscriber3);
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
