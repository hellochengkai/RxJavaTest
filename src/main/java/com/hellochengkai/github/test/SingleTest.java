package com.hellochengkai.github.test;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;

import java.util.function.Function;

public class SingleTest implements Function {
    @Override
    public Object apply(Object o) {
        /**
         * Single发射单个或者错误事件，无onNext
         */
        SingleObserver singleObserver = new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("SingleTest.onSubscribe");
            }

            @Override
            public void onSuccess(String s) {
                System.out.println("SingleTest.onSuccess " + s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("SingleTest.onError " + e.getMessage());
            }
        };
        Single.create((SingleOnSubscribe<String>) emitter -> {
            emitter.onSuccess("This is ok");
            //只发送一次事件，后面的都不会再发送
            emitter.onSuccess("This is ok1");
            emitter.onSuccess("This is ok2");
            emitter.onSuccess("This is ok3");
//            emitter.onError(new Throwable("myError"));
        }).subscribe(singleObserver);

        //可以通过toXXXX进行转变
        Single.just("haha").toObservable();
        Single.just("haha").toFlowable();
        Single.just("haha").toFuture();
        Single.just("haha").toMaybe();

//        Single.just("aaa").subscribe(singleObserver);
        return null;
    }
}
