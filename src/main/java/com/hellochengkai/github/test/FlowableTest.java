package com.hellochengkai.github.test;

import io.reactivex.*;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FlowableTest implements Runnable {

    @Override
    public void run() {
        /**
         * Flowable 支持背压
         * BackpressureStrategy 背压策略
         * MISSING 没有任何缓存和丢弃事件,
         * ERROR 缓冲大于缓冲队列的大小（默认是128）会抛出异常
         * BUFFER 对缓冲没有限制，会一直发送数据，事件。有可能会出现OOM
         * DROP 会丢弃大于缓冲队列的数据，不会报错
         * LATEST 不管缓冲池的状态如何，最后一个数据总会发送
         * 指定背压策略的方法有两种
         * create BackpressureStrategy.XXXX
         * 和使用操作符
         * .onBackpressureDrop()
         * .onBackpressureLatest()
         * .onBackpressureBuffer();
         */

        /**
         * MISSING:没有任何缓存和丢弃，下游要处理任何溢出
         * ERROR:下游的处理速度无法跟上上游的发射速度时报错
         * BUFFER:数据项的缓存池无限大
         * DROP:下游的处理速度无法跟上上游的发射速度时丢弃
         * LATEST:最后一条数据项被强行放入缓存池
         */
//        Flowable.create((FlowableOnSubscribe<Integer>) emitter -> Observable.range(1,129).subscribe(integer -> emitter.onNext(integer)),BackpressureStrategy.BUFFER)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.newThread())
//                .subscribe(MyFlowableSubscriber.create("a"));


//        Observable.range(1,1000)
//                .doOnNext(integer -> System.out.println("FlowableTest.run send" + integer))
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.newThread())
//                .subscribe(integer -> {
//                    Thread.sleep(1000);
//                    System.out.println("FlowableTest.accept" + integer);
//                });

        Flowable.create(
                (FlowableOnSubscribe<Integer>) emitter -> Observable.range(1, 1219).subscribe(integer -> emitter.onNext(integer)),
                    BackpressureStrategy.DROP)
                .doOnNext(integer -> {
                    Thread.sleep(10);
                    System.out.println("FlowableTest.run send" + integer);
                })
                .onBackpressureDrop(integer -> System.out.println("FlowableTest.accept Drop " + integer))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(integer -> {
                    Thread.sleep(20);
                    System.out.println("FlowableTest.accept" + integer);
                });
    }
}
