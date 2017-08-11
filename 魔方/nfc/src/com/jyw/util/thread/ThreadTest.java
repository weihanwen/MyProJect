package com.jyw.util.thread;

import java.util.concurrent.TimeUnit;

/**
 * runnable线程
 * @author 魏氏
 *
 */
public class ThreadTest implements Runnable{
	
	protected int countDow = 3;
	
	private static int taskCount = 0;//第一次调用函数，static变量，初始化,第二次，及以后，调用函数，static变量，不会初始化。
	
	private final int id = taskCount++;
	
	public ThreadTest(){
		
	}
	public ThreadTest(int countDow){
		this.countDow=countDow;
	}
	
	public  String status(){
		return "#"+id+"("+( countDow > 0 ? countDow:"countDow小于等于0")+");";
	}

	@Override
	public void run() {
		
 		try {
			// TODO Auto-generated method stub
	 		while(countDow-- >0){
	  			System.out.println(status());
	 			Thread.yield();//线程调度器;将一个线程转移到另外一个线程
	  			TimeUnit.MILLISECONDS.sleep(100);
	  			
			}
		} catch (InterruptedException e) {
			// TODO: handle exception
			System.out.println("InterruptedException");
 		}finally{
			
		}
		
		
		
	}

}
