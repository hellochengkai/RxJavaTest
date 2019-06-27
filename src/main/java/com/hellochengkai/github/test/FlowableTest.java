package com.hellochengkai.github.test;

import com.hellochengkai.github.MyFlowableSubscriber;
import io.reactivex.*;
import io.reactivex.schedulers.Schedulers;

import javax.security.auth.Subject;

public class FlowableTest implements Runnable {

    @Override
    public void run() {
        /**
         * Flowable 支持背压
         * BackpressureStrategy 背压策略
         * MISSING 没有任何缓存和丢弃事件
         * ERROR
         * BUFFER
         * DROP
         * LATEST
         * 指定背压策略的方法有两种
         * create BackpressureStrategy.XXXX
         * 和使用操作符
         * .onBackpressureDrop()
         * .onBackpressureLatest()
         * .onBackpressureBuffer();
         */
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Observable.range(1,124).subscribe(integer -> emitter.onNext(integer));
                emitter.onComplete();
            }
        },BackpressureStrategy.MISSING)
                .observeOn(Schedulers.newThread())
                .subscribe(MyFlowableSubscriber.create("a"));
    }
}
