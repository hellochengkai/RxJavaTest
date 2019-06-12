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

public class RefCountTest implements Runnable{
    @Override
    public void run() {
        //cold --> hot --> cold
/**
 *
 * Hot Observable也称作为可连接的Observable(ConnectableObservable)。Cool Observable就称作为普通的Observable
 *
 */
        /**
         * RefCount操作符把从一个可连接的 Observable 连接和断开的过程自动化了。
         * 它操作一个可连接的Observable，返回一个普通的Observable。
         * 当第一个订阅者订阅这个Observable时，RefCount连接到下层的可连接Observable。
         * RefCount跟踪有多少个观察者订阅它，直到最后一个观察者完成才断开与下层可连接Observable的连接。
         * 如果所有的订阅者都取消订阅了，则数据流停止。如果重新订阅则重新开始数据流。
         */
        Consumer<Long> subscriber1 = aLong -> System.out.println("subscriber1: "+aLong);

        Consumer<Long> subscriber2 = aLong -> System.out.println("   subscriber2: "+aLong);

        ConnectableObservable<Long> connectableObservable = /*cold observable*/Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {
                Observable.interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .take(Integer.MAX_VALUE)
                        .subscribe(e::onNext);
                System.out.println("asdfasdf RefCountTest.subscribe");
            }
        }).observeOn(Schedulers.newThread()).publish();/*publish会产生 hot observable*/

        /**
         * publish将 Observable --> ConnectableObservable
         *
         * refCount 将ConnectableObservable --> Observable
         */
//        connectableObservable.connect();/*connect之后hot observable 开始发送事件,当使用refCount时可以不用调用*/
        //refCount 将hot observable 转为 cold observable。
        //refCount之后变成cold observable 就可以不用使用 connect 来出发上游的 observable 发送事件，
        //当下游的订阅者subscribe时就会出发上游的 observable 发送事件
        Observable<Long> observable = connectableObservable.refCount();
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
