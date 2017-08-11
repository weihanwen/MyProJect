package com.jyw.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PriorityTest implements Runnable{
	
	private int countDow=5;
	
	private volatile double d;
	
	private int priority;
	
	public  PriorityTest(int priority){
		this.priority=priority;
	}
	
	public String toString(){
		return Thread.currentThread()+"============"+countDow;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread.currentThread().setPriority(priority);
		while(true){
			for(int i=1; i<10000 ; i++){
				d+=(Math.PI+Math.E)/(double)i;
				if(i%1000 == 0){
					Thread.yield();
				}
				
			}
			System.out.println(this);//输出toString()
			if(--countDow == 0){ 
				return;
			}
		}
 	}
	
	
	public static void main(String[] args) {
		ExecutorService ex=Executors.newCachedThreadPool();
		for (int i = 0; i < 5 ; i++) {
			ex.execute(new PriorityTest(Thread.MAX_PRIORITY));
		} 
		ex.execute(new PriorityTest(Thread.MIN_PRIORITY));
		ex.shutdown();
  	}
	
	

}
