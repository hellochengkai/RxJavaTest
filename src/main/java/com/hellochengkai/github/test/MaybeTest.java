package com.hellochengkai.github.test;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MaybeTest implements Runnable{
    @Override
    public void run() {
        /**
         * Maybe 发射0个或者1个数据。要么成功，要么失败
         */
        MaybeObserver maybeObserver = new MaybeObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("MaybeTest.onSubscribe");
            }

            @Override
            public void onSuccess(Object o) {
                System.out.println("MaybeTest.onSuccess " + o);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("MaybeTest.onError");
            }

            @Override
            public void onComplete() {
                System.out.println("MaybeTest.onComplete");
            }
        };

        Maybe.create((MaybeOnSubscribe<String>) emitter -> {
            emitter.onComplete();//调用onComplete之后onSuccess会无效
            emitter.onSuccess("hello Maybe");
        }).subscribe(maybeObserver);

        Maybe.create((MaybeOnSubscribe<String>) emitter -> {
            emitter.onSuccess("1111");
//            emitter.onSuccess("2222");//数据无效，只发送第一个数据
            emitter.onComplete();//有数据发送，或者执行错误的话onComplete不会再执行
        }).subscribe(maybeObserver);
        Maybe.create((MaybeOnSubscribe<String>) emitter -> {
            emitter.onComplete();//在没有发送数据时，onComplete会被执行
        }).subscribe(maybeObserver);
//        Maybe.just("aaa").subscribe(maybeObserver);
        /**
         * Maybe 只会执行 onSuccess  onComplete onError 中的一个，
         */

    }
}
