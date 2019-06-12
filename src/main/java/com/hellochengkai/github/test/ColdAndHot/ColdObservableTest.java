package com.hellochengkai.github.test.ColdAndHot;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class ColdObservableTest implements Runnable {
    //
    @Override
    public void run() {
        Consumer<Long> subscriber1 = aLong -> System.out.println("subscriber1: " + aLong);

        Consumer<Long> subscriber2 = aLong -> System.out.println("   subscriber2: " + aLong);
        Observable<Long> observable = Observable.create((ObservableOnSubscribe<Long>) e -> Observable.interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
                .take(Integer.MAX_VALUE)
                .subscribe(e::onNext)).observeOn(Schedulers.newThread());

        observable.subscribe(subscriber1);
        observable.subscribe(subscriber2);

        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
