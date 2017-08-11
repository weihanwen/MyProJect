package com.jyw.util.thread;

import java.util.concurrent.Callable;

/**
 * 带返回值嘚线程
 * @author 魏氏
 *
 */
public class CallableTest implements Callable<String> {//获取返回结果的线程
 
	private  int id=0;
	
	public CallableTest(int id){
		this.id=id;
	}

	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		return "now id is =="+id;
	}

}
