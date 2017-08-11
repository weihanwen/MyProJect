package com.jyw.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThread {

	public static void main(String[] args) {
//		ThreadTest t=new ThreadTest();
// 		t.run();
//		System.out.println("waiting for zero");// 输出结果是等run执行完毕之后才输入waiting for zero
		
//		Thread t=new Thread(new ThreadTest());
//		t.start();
//		System.out.println("use thread waiting");//单线程--输出结果是run方法新开一线程，秒过这部分，可以同行执行下面的代码。并行
//		
		
//		for (int i = 0; i < 2; i++) {
// 			new Thread(new ThreadTest()).start();
// 		}//多线程是同步的执行，互不干桃
//		System.out.println("use thread waiting");
		
		ExecutorService es=Executors.newCachedThreadPool();//有多少个线程，创建多少个线程的线程池
		for (int i = 0; i < 5; i++) {
			es.execute(new ThreadTest(i));
 		}
		es.shutdown();//在运行完所有线程，执行关闭
		System.out.println("线程管理cached thread waiting");
		
//		ExecutorService ex2=Executors.newFixedThreadPool(3);//管理线程池，一次只能运行完指定的线程，等线程池释放后继续
//		for (int i = 0; i < 5; i++) {
//			ex2.execute(new ThreadTest());
// 		}
//		ex2.shutdown();
//		System.out.println("线程管理fixed thread waiting");
		
		
//		ExecutorService ex3=Executors.newSingleThreadExecutor();
//		for (int i = 0; i < 5; i++) {
//			ex3.execute(new ThreadTest());
//		}
//		ex3.shutdown();
//		System.out.println("线程管理single ");
		
	}
	
}
