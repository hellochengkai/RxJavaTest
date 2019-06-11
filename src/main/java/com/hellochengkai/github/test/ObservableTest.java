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
