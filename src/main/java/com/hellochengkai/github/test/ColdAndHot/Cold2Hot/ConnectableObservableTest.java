package com.hellochengkai.github.test.ColdAndHot.Cold2Hot;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class ConnectableObservableTest implements Runnable {
    @Override
    public void run() {
        Consumer<Long> subscriber1 = aLong -> System.out.println("subscriber1: " + aLong);

        Consumer<Long> subscriber2 = aLong -> System.out.println("   subscriber2: " + aLong);

        Consumer<Long> subscriber3 = aLong -> System.out.println("      subscriber3: " + aLong);


        ConnectableObservable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {
                Observable.interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .take(Integer.MAX_VALUE)
                        .subscribe(e::onNext);
                System.out.println("asdfasdf ConnectableObservableTest.subscribe");
                //在调用  observable.connect(); 之后 subscribe 就触发了，
                //也就是说Hot Observable数据发射的时机是执行connect()之后。
            }
        }).observeOn(Schedulers.newThread()).publish();
        Disposable disposable1 = observable.subscribe(subscriber1);
        Disposable disposable2 = observable.subscribe(subscriber2);
        Disposable disposable = observable.connect();
        //调用connect返回的 disposable.dispose()可以取消事件的发送
        //disposable.dispose();
        try {
            Thread.sleep(80L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable1.dispose();
        disposable2.dispose();
        //Hot observable：所有的订阅者都取消了订阅以后，被观察者的事件不会重置，会继续发送。
        observable.subscribe(subscriber3);
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
