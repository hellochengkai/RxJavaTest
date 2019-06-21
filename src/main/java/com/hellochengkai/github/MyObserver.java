package com.hellochengkai.github;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MyObserver implements Observer <String>{

    public static MyObserver create(String name)
    {
        return new MyObserver(name);
    }


    private String name;

    private MyObserver(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Disposable d) {
        System.out.println("\n" + name + " : MyObserver.onSubscribe at " +  + Thread.currentThread().getId());
    }

    @Override
    public void onNext(String s) {
        System.out.println(name + " : MyObserver.onNext " + s + " at " +  + Thread.currentThread().getId());
    }

    @Override
    public void onError(Throwable e) {
        System.out.println(name + " : MyObserver.onError at " + Thread.currentThread().getId() + "\n");
    }

    @Override
    public void onComplete() {
        System.out.println(name + " : MyObserver.onComplete at " + Thread.currentThread().getId() + "\n");
    }
}
