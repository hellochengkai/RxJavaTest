package com.hellochengkai.github.operator;

import com.hellochengkai.github.MyObserver;
import io.reactivex.Observable;

public class BufferTest implements Runnable{
    @Override
    public void run() {
        Observable.range(1,20).buffer(5).subscribe(MyObserver.create("buffer"));
        Observable.range(1,20).buffer(5,5).subscribe(MyObserver.create("buffer"));
    }
}
