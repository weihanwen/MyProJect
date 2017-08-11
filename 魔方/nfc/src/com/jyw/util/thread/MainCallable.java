package com.jyw.util.thread;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainCallable {

	public static void main(String[] args) {
		ExecutorService ex=Executors.newCachedThreadPool();//线程池管理
		ArrayList<Future<String>> af=new ArrayList<Future<String>>();
		for (int i = 0; i < 5 ; i++) {
			af.add(ex.submit(new CallableTest(i)));
		}
		
		for (Future<String> future : af) {
			try {
 				if(future.isDone()){
					System.out.println(future.get());
				}
 			} catch(InterruptedException e){
				System.out.println(e.toString());//它告诉您它是一个阻塞（blocking）方法，如果您响应得当的话，它将尝试消除阻塞并尽早返回。
				return;
			}catch(ExecutionException e){
				System.out.println(e.toString());
			}finally{
				ex.shutdown();
			}
		}
		
		
	}
}
