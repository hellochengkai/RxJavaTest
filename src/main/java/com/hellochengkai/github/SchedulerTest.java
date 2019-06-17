package com.hellochengkai.github;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class SchedulerTest implements Runnable {
    @Override
    public void run() {
        new Thread(() -> {
            Observable.create((ObservableOnSubscribe<String>) emitter -> {
                System.out.println("SchedulerTest.run " + Thread.currentThread().getId());
                emitter.onNext("hello repeat ");
                emitter.onComplete();
            })
                    .observeOn(Schedulers.newThread())
                    .subscribe(MyObserver.create("create "));
            System.out.println("SchedulerTest.run at " + Thread.currentThread().getId());

        }).start();


//        Observable.create((ObservableOnSubscribe<String>) emitter -> {
//            System.out.println("SchedulerTest.run " + Thread.currentThread().getId());
//            emitter.onNext("hello repeat");
//            emitter.onComplete();
//        })
//                .subscribe(MyObserver.create("create"));
//        System.out.println("SchedulerTest.run " + Thread.currentThread().getId());

    }
}
