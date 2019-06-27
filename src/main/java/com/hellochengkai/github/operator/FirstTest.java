package com.hellochengkai.github.operator;


import io.reactivex.Observable;

public class FirstTest implements Runnable {
    @Override
    public void run() {
        /**
         * first last 创建的事 Single
         */
        Observable.<Integer>empty()
                .first(2)
                .subscribe(integer -> System.out.println("FirstTest.one " + integer));

        Observable.fromArray()
                .first(100)
                .subscribe(integer -> System.out.println("FirstTest.tow " + integer));

        Observable.range(1,10)
                .first(100)
                .subscribe(integer -> System.out.println("FirstTest.tow " + integer));

        Observable.fromArray(5,4,3,32,5,6,2)
                .first(100)
                .subscribe(integer -> System.out.println("FirstTest.tow " + integer));
    }
}
