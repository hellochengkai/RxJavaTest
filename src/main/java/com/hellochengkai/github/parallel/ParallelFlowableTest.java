package com.hellochengkai.github.parallel;


import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class ParallelFlowableTest implements Runnable {
    @Override
    public void run() {
        Flowable.range(1,100).parallel()
                .runOn(Schedulers.io())
                .map(integer -> integer.toString())
                .sequential()
                .subscribe(s -> System.out.println("ParallelFlowableTest.accept my parallel flowable test" + s ));

    }
}
