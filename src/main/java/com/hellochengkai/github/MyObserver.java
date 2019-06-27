package com.hellochengkai.github;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MyObserver<T> implements Observer <T>{

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
        System.out.println("\n" + name + " : MyObserver.onSubscribe at " + Thread.currentThread().getName());
    }

    @Override
    public void onNext(T s) {
        System.out.println(name + " : MyObserver.onNext(" + s + ") at " + Thread.currentThread().getName());
    }

    @Override
    public void onError(Throwable e) {
        System.out.println(name + " : MyObserver.onError at " + Thread.currentThread().getName() + "\n");
    }

    @Override
    public void onComplete() {
        System.out.println(name + " : MyObserver.onComplete at " + Thread.currentThread().getName() + "\n");
    }
}
