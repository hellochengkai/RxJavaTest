package com.hellochengkai.github;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
public class MyObserver<T> implements Observer <T>{

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    static final String TAG = MyObserver.class.getSimpleName();
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
        compositeDisposable.add(d);
        System.out.println("\n" + name + " : " + TAG + ".onSubscribe at " + Thread.currentThread().getName());
    }

    @Override
    public void onNext(T t) {
        System.out.println(name + " : " + TAG + ".onNext(" + t + ") at " + Thread.currentThread().getName());
    }

    @Override
    public void onError(Throwable t) {
        compositeDisposable.clear();
        System.out.println(name + " : " + TAG + ".onError at " + Thread.currentThread().getName() + "\n");
    }

    @Override
    public void onComplete() {
        System.out.println(name + " : " + TAG + ".onComplete at " + Thread.currentThread().getName() + "\n");
    }
}
