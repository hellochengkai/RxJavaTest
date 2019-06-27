package com.hellochengkai.github;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class SchedulerTest implements Runnable {
    @Override
    public void run() {
//        new Thread(() -> {
//            Observable.create((ObservableOnSubscribe<String>) emitter -> {
//                System.out.println("SchedulerTest.run " + Thread.currentThread().getId());
//                emitter.onNext("hello repeat ");
//                emitter.onComplete();
//            })
//                    .observeOn(Schedulers.newThread())
//                    .subscribe(MyObserver.create("create "));
//            System.out.println("SchedulerTest.run at " + Thread.currentThread().getId());
//
//        }).start();


//        Observable.create((ObservableOnSubscribe<String>) emitter -> {
//            System.out.println("SchedulerTest.run " + Thread.currentThread().getId());
//            emitter.onNext("hello repeat");
//            emitter.onComplete();
//        })
//                .subscribe(MyObserver.create("create"));
//        Observable.create((ObservableOnSubscribe<Integer>) observableEmitter -> {
//            System.out.println("ObservableOnSubscribe.subscribe thread : " + Thread.currentThread().getName());
//            observableEmitter.onNext(1);
//            observableEmitter.onComplete();
//        })//顶层Observable
//                .subscribeOn(Schedulers.io())//第一次subscribeOn
//                .map(integer -> {
//
//                    System.out.println("map  thread : " + Thread.currentThread().getName());
//                    return String.valueOf(integer);
//                })//第二个Observable
//                .subscribeOn(Schedulers.newThread())//第二次subscribeOn
//                .filter(s -> {
//                    System.out.println("filter  thread : " + Thread.currentThread().getName());
//                    return Integer.parseInt(s) > 0;
//                })//第三个Observable
//                .subscribe(MyObserver.create("subscribeOn test"));
//
//        Observable.create((ObservableOnSubscribe<Integer>) observableEmitter -> {
//            observableEmitter.onNext(1);
//            observableEmitter.onComplete();
//            System.out.println("observableEmitter : " + observableEmitter.getClass().getName());
//        })//顶层 Observable
//                .subscribeOn(Schedulers.newThread())
//                .map(integer -> {
//                    System.out.println("map  thread aa: " + Arrays.toString(Thread.currentThread().getStackTrace()));
//                    return String.valueOf(integer);
//                })//第二个 Observable
//                .subscribe();

        Observable.create((ObservableOnSubscribe<Integer>) observableEmitter -> {
            observableEmitter.onNext(1);
            observableEmitter.onComplete();
        })//顶层 Observable
                .map(integer -> {
                    System.out.println("filter  map2 : " + Thread.currentThread().getName());
                    return integer + " map2 ";
                })
                .observeOn(Schedulers.computation())
                .subscribeOn(Schedulers.newThread())
                .subscribe(MyObserver.create("my test"));
    }
}
