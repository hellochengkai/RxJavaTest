package com.hellochengkai.github.operator;


import com.hellochengkai.github.MyObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import java.util.concurrent.TimeUnit;

public class RepeatTest implements Runnable {

    @Override
    public void run() {
        /**
         * repeat 不是创建一个Observe,而是重复发送某个数据,
         * 在执行完onComplete之前触发repeat,
         * 不调用onComplete时不会进行数据的重复发送
         */
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            System.out.println("RepeatTest.run" + Thread.currentThread().getId());
            emitter.onNext("hello repeat");
            emitter.onComplete();
        })
                .repeat(10).subscribe(MyObserver.create("create"));

        Observable.just("hello repeat")
                .repeat(10)
                .subscribe(MyObserver.create("just"));


        Observable.just("hello repeat", "hello repeat ex")
                .repeat(10)
                .subscribe(MyObserver.create("just ex"));


        Observable.fromArray("hello repeat", "hello repeat ex")
                .repeat(10)
                .subscribe(MyObserver.create("fromArray"));


        //repeatWhen
        Observable.just("hello repeat")
                .repeatWhen(objectObservable ->
                        Observable.timer(2, TimeUnit.SECONDS))//2秒之后再发送一次
                .subscribe(MyObserver.create("just repeatWhen "));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
