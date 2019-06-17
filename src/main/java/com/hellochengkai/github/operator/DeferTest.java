package com.hellochengkai.github.operator;


import com.hellochengkai.github.MyObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;

import java.util.concurrent.Callable;

public class DeferTest implements Runnable{
    @Override
    public void run() {
        /**
         * defer 知道有观察者订阅时才会创建Observable,并且为每个观察者创建一个新的Observable
         */

        Observable observable = Observable.defer((Callable<ObservableSource<?>>) () -> {
            System.out.println("DeferTest.call");
            return Observable.just("hello defer");
        });

        System.out.println("sleep 10000ms");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        observable.subscribe(MyObserver.create("defer"));


    }
}
