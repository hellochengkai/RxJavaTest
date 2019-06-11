package com.hellochengkai.github.test;

import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.function.Function;

public class FlowableTest implements Function {
    @Override
    public Object apply(Object o) {
        /**
         * Flowable 发射0-n个事件，并以错误或者成功结束,支持背压
         */
        Flowable.just("aaaa").subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("FlowableTest.onSubscribe");
            }

            @Override
            public void onNext(String s) {
                System.out.println("FlowableTest.onNext " + s);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("FlowableTest.onError");
            }

            @Override
            public void onComplete() {
                System.out.println("FlowableTest.onComplete");
            }
        });
        return null;
    }
}
