package com.hellochengkai.github.parallel;

import com.hellochengkai.github.MyObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelTest implements Runnable {
    @Override
    public void run() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }


        int threadNum = Runtime.getRuntime().availableProcessors() + 1;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

        Scheduler scheduler = Schedulers.from(executorService);


//        list.stream().map(integer -> "" + integer).forEach(s -> System.out.println("ParallelTest.stream s = " + s + " run at" + Thread.currentThread().getName()));
//
//        /**
//         * java 8 stream 提供的Stream支持 并行操作 parallelStream
//         * 基于并发实现的并行操作
//         */
//        list.parallelStream().map(integer -> "" + integer).forEach(s -> System.out.println("ParallelTest.parallelStream s = " + s + " run at" + Thread.currentThread().getName()));
//

        /**
         * rxjava 使用flatMap实现数据流的并行处理，将每个任务重新创建成 Observable 并分发到非订阅线程中（异步分发），
         * 从而实现数据的并行处理，虽然并行但不并发
         */
//        final int[] num = {0};
//        Observable.range(1,100)
//                .flatMap((Function<Integer, ObservableSource<String>>) integer -> Observable
//                        .just(integer)
//                        .subscribeOn(Schedulers.computation())
//                        .map(integer1 -> {
//                            num[0]++;
////                            System.out.println("--------------------------------------------------------------ParallelTest.run " + Thread.currentThread().getName());
//                            return integer1 + "";
//                        })
//                )
//                /**
//                 * num[0] 可能不会为100
//                 */
//                .doFinally(() -> System.out.println("ParallelTest.run doFinally num = " + num[0]))
//                .subscribe(MyObserver.create("Observable + flatMap"));



        Observable.range(1, 100)
                .flatMap((Function<Integer, ObservableSource<String>>)
                        integer ->
                                Observable
                                        .just(integer)
                                        .observeOn(scheduler)
                                        .map(integer1 -> integer1 + "")
                )
                .doFinally(() -> executorService.shutdownNow())
//                .subscribe();
                .subscribe(MyObserver.create("Observable + flatMap + scheduler"));


//        AtomicInteger atomicInteger = new AtomicInteger();
//
//        Observable.range(1, 100)
//                .groupBy(integer -> atomicInteger.getAndIncrement() % 5)
//                .flatMap((Function<GroupedObservable<Integer, Integer>, ObservableSource<?>>)
//                                integerIntegerGroupedObservable ->
//                                        integerIntegerGroupedObservable
//                                                .subscribeOn(Schedulers.newThread())
//                                                .map(integer -> {
//                                                    System.out.println("ParallelTest.run " + Thread.currentThread().getName());
//                                                    return integer + "";
//                                                })
//                )
//                .doFinally(() -> executorService.shutdownNow())
//                .subscribe();
//
//


    }
}
