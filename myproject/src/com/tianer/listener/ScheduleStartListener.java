package com.tianer.listener;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.quartz.ConfirmOrderQuartz;
import com.tianer.util.quartz.DeleteOrderQuartz;
public class ScheduleStartListener implements ServletContextListener{	
	
	public void contextDestroyed(ServletContextEvent sce){
		//ServiceHelper
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();
			sched.shutdown();
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void contextInitialized(ServletContextEvent sce){		
		try {
			System.out.print("定时任务启动开始");
			recovery();
			System.out.print("定时任务启动结束");
		}catch (Exception e){
		}
	}
	public void recovery(){
		DeleteOrderQuartz.addJob();
		PageData pd = new PageData();
		try {
			System.out.println("----------- 定时任务 ----------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//定时循环
	public static void main(String[] args) {  
        // run in a second  
        final long timeInterval = 1000; //毫秒为单位：1秒=1000毫秒 
        Runnable runnable = new Runnable() {  
            public void run() {  
            	int i=0;
            	System.out.println("start of time");
                while (true) {  
                    // ------- code for task to run  
                    i++;
                    System.out.println(i+"--Hello !!");  
                    if(i== 10){
                    	break;
                    }
                    // ------- ends here  
                    try {  
                        Thread.sleep(timeInterval);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
                System.out.println("End of time");  
            }  
        };  
        Thread thread = new Thread(runnable);  
        thread.start();  
        
        
        /*final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            private int count;
            public void run() {
                this.count++;
                System.out.println(count);
                if (count == 10) {
                    System.out.println("定时器停止了");
                    timer.cancel();// 停止定时器
                }
            }
        };
        timer.schedule(task, 0, 1000);// 1秒一次 */
        
    }  
}
