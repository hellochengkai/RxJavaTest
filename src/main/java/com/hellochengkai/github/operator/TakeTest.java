package com.hellochengkai.github.operator;


import com.hellochengkai.github.MyObserver;
import io.reactivex.Observable;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class TakeTest implements Runnable {
    @Override
    public void run() {
        /**
         * take 之发射前面几个数据
         * takeLast 之发射后面几个数据
         */
        Observable.just(1,3,4,5,6,7,9).take(3).subscribe(MyObserver.create("take1"));
        Observable.just(1,3).take(5).subscribe(MyObserver.create("take2"));

        Observable.intervalRange(0,10,1,1, TimeUnit.SECONDS)
                .take(3,TimeUnit.SECONDS)
                .subscribe(MyObserver.create("take time "));

        Observable.just(1,3,4,5,6,7,9).takeLast(3).subscribe(MyObserver.create("takeLast1"));
        Observable.just(1,3).takeLast(5).subscribe(MyObserver.create("takeLast2"));

        Observable.intervalRange(0,10,1,1, TimeUnit.SECONDS)
                .takeLast(3,TimeUnit.SECONDS)
                .subscribe(MyObserver.create("takeLast time "));
    }
}
