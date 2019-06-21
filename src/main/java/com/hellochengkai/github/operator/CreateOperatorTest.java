package com.hellochengkai.github.operator;

import com.hellochengkai.github.MyObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;

public class CreateOperatorTest implements Runnable{


    //Just From Create
    @Override
    public void run() {
        String[] strings = {"AAA ","BBB","CCC"};

        //将一个或者多个对象转换成发射这个对象的Observable
        Observable.just(strings[0],strings[1],strings[2]).subscribe(MyObserver.create("just"));

        //将一个或者多个对象转换成发射这个对象的Observable
        Observable.fromArray(strings).subscribe(MyObserver.create("fromArray"));


        FutureTask futureTask = new FutureTask(() -> {
            System.out.println("Future is Running!");
            Thread.sleep(2000);
            return "Future ThreadID is " + Thread.currentThread().getId();
        });

        new Thread(futureTask).start();
        Observable.fromFuture(futureTask).subscribeOn(Schedulers.newThread()).subscribe(MyObserver.create("fromFuture"));
        //可以设置超时时间
        Observable.fromFuture(futureTask,1,TimeUnit.SECONDS).subscribeOn(Schedulers.newThread()).subscribe(MyObserver.create("fromFuture  time out"));


        Observable.fromIterable(new ArrayList(Arrays.asList(strings))).subscribe(MyObserver.create("fromIterable"));
        Observable.fromPublisher((Publisher<String>) s -> {
            s.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {

                }

                @Override
                public void cancel() {

                }
            });
            s.onNext(strings[0]);
            s.onNext(strings[1]);
            s.onNext(strings[2]);
            s.onComplete();
        }).subscribe(MyObserver.create("fromPublisher"));

        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            if(emitter.isDisposed()){
                return;
            }
            emitter.onNext(strings[0]);
            emitter.onNext(strings[1]);
            emitter.onNext(strings[2]);
            emitter.onComplete();
        }).subscribe(MyObserver.create("create"));
    }
}
