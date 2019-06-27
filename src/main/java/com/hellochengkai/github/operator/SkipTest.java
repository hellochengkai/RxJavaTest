package com.hellochengkai.github.operator;

import com.hellochengkai.github.MyObserver;
import io.reactivex.Observable;

public class SkipTest implements Runnable {
    @Override
    public void run() {
        /**
         * skip 跳过前面的n个数据
         * skipLast 跳过后面的n个数据
         */
        Observable.range(1,100).skip(12).subscribe(MyObserver.create("skip"));
        Observable.range(1,100).skipLast(12).subscribe(MyObserver.create("skip"));

        Observable.range(1,4).skip(12).subscribe(MyObserver.create("skip"));
        Observable.range(1,4).skipLast(12).subscribe(MyObserver.create("skip"));

        Observable.range(1,13).skip(12).subscribe(MyObserver.create("skip"));
        Observable.range(1,13).skipLast(12).subscribe(MyObserver.create("skip"));
    }
}
