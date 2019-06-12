package com.hellochengkai.github.test;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.function.Function;
public class ObservableTest implements Function {

    @Override
    public Object apply(Object o) {
        /**
         * Observable 发射0-n个事件，并以错误或者成功结束
         *
         * Observable 的 just、creat、range、fromXXX 等操作符都能生成Cold Observable。
         * cold Observable 和 hot Observable，区别
         * hot闭关是否被订阅都会触发事件，
         *  有多个订阅者时，Observable和订阅者们是一对多的，订阅者收到的时间都是同一个事件，类似广播
         * cold只有在被订阅时才会触发事件
         *  有多个订阅者时，Observable和订阅者们是一对一的，背个订阅这收到的都是一个新的事件
         */

        Observable.just("asdfasd").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("ObservableTest.onSubscribe");
            }

            @Override
            public void onNext(String s) {
                System.out.println("ObservableTest.onNext" + "s = " + s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("ObservableTest.onError " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("ObservableTest.onComplete");
            }
        });
        return null;
    }
}
