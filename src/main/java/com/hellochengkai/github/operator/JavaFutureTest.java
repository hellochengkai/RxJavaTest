package com.hellochengkai.github.operator;


import java.util.concurrent.*;

public class JavaFutureTest implements Runnable {

    /**
     * 　　在前面的文章中我们讲述了创建线程的2种方式，一种是直接继承Thread，另外一种就是实现Runnable接口。
     *
     * 　　这2种方式都有一个缺陷就是：在执行完任务之后无法获取执行结果。
     *
     * 　　如果需要获取执行结果，就必须通过共享变量或者使用线程通信的方式来达到效果，这样使用起来就比较麻烦。
     *
     * 　　而自从Java 1.5开始，就提供了Callable和Future，通过它们可以在任务执行完毕之后得到任务执行结果。
     */

    /**
     *  　　在Future接口中声明了5个方法，下面依次解释每个方法的作用：
     *
     * cancel 方法用来取消任务，如果取消任务成功则返回true，如果取消任务失败则返回false。
     *  参数mayInterruptIfRunning表示是否允许取消正在执行却没有执行完毕的任务，如果设置true，
     *  则表示可以取消正在执行过程中的任务。如果任务已经完成，则无论mayInterruptIfRunning为true还是false，
     *  此方法肯定返回false，即如果取消已经完成的任务会返回false；如果任务正在执行，
     *  若mayInterruptIfRunning设置为true，则返回true，若mayInterruptIfRunning设置为false，
     *  则返回false；如果任务还没有执行，则无论mayInterruptIfRunning为true还是false，肯定返回true。
     *
     * isCancelled 方法表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
     *
     * isDone 方法表示任务是否已经完成，若任务完成，则返回true；
     *
     * get() 方法用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回；
     *
     * get(long timeout, TimeUnit unit) 用来获取执行结果，如果在指定时间内，还没获取到结果，就直接返回null。
     *
     * 　　也就是说Future提供了三种功能：
     *
     * 　　1）判断任务是否完成；
     *
     * 　　2）能够中断任务；
     *
     * 　　3）能够获取任务执行结果。
     */
    /**
     * FutureTask是Future接口的一个唯一实现类。
     */

    ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void run() {

//        run1();
        run2();
        executor.shutdown();
    }


    private void run1() {

        //submit 提交一个 Callable 带返回值的任务 到线程池,返回一个Future
        Future<String> result = executor.submit(new Task("run1 task1"));

        Future<String> result1 = executor.submit(new Task("run1 task2"));


        System.out.println("主线程在执行任务");
        result1.cancel(true);//取消一个任务

        try {

            if (result.isDone()) {
                System.out.println("task运行结果" + result.get());
            } else {
                System.out.println("task1还在运行" + result1.isDone());
            }

            if (!result1.isCancelled()) {
                System.out.println("task1运行结果" + result1.get());
            } else {
                System.out.println("task1已经取消" + result1.isCancelled());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");
    }


    private void run2() {

        //submit 提交一个 Callable 带返回值的任务 到线程池,返回一个Future
        //Future<String> result = executor.submit(new Task("run1 task1"));

        //FutureTask 将Callable转换为Runnable?将 Runnable 和 Future 结合?
        FutureTask<String> result = new FutureTask(new Task("run2 task1"));
        executor.submit(result);

        //FutureTask 将Callable转换为Runnable以后就可以直接使用线程进行任务的执行
        FutureTask<Integer> futureTask = new FutureTask(new Task("run2 task2"));
        Thread thread = new Thread(futureTask);
        thread.start();

        System.out.println("主线程在执行任务");
        try {

            if (!result.isCancelled()) {
                System.out.println("task1运行结果" + result.get());
            } else {
                System.out.println("task1已经取消" + result.isCancelled());
            }

            if (!futureTask.isCancelled()) {
                System.out.println("task1运行结果" + futureTask.get());
            } else {
                System.out.println("task1已经取消" + futureTask.isCancelled());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");
    }
}


class Task implements Callable<String> {
    String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        System.out.println(name + " is Running!");
        Thread.sleep(1000);
        return "from " + name + " ThreadID is " + Thread.currentThread().getId();
    }
}