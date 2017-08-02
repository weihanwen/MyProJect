package com.jyw.util;
 
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.util.JSONPObject;

import com.jyw.entity.system.AppValidation;
import com.jyw.util.address.MapDistance;

public class AppUtil  {
	
	protected static Logger logger = Logger.getLogger(AppUtil.class);
	public static void main(String[] args) {
		PageData pd = new PageData();
		pd.put("username", "zhangsan");
		
		checkParam("registered", pd);
	}
	
	//获取Ip展示地址
		public static String getuploadRootUrlIp(){
			String url = "http://192.168.1.12/FileSave/";
	 		return url;
		}	
			
	    //获取展示地址
		public static String getuploadRootUrl(){
			String url = "E://dzh//apache-tomcat-7.0.63//FileSave";
	 		return url;
	 	}	
	
	//检查参数是否完整
	public static Map<String,String> checkParam(String beanId, PageData pd){
		Map<String,String> result = new HashMap<String,String>();
		if(Const.APPVALIDATION.containsKey(beanId)){
			Map<String,AppValidation> appValidation = Const.APPVALIDATION.get(beanId);
			for(Map.Entry<String, AppValidation> entry : appValidation.entrySet()){   
				AppValidation validation = entry.getValue();
				if(null==pd.get(entry.getKey())&&"true".equals(validation.getRequired())){
					result.put(entry.getKey(), "必填");
				} 
				if(validation.getMaxlength()<pd.getString(entry.getKey()).length()){
					result.put(entry.getKey(), "超出最大长度");
				} 
				if(validation.getMinlength()>pd.getString(entry.getKey()).length()){
					result.put(entry.getKey(), "低于最小长度");
				} 
				if(Tools.notEmpty(validation.getMin())&&Integer.parseInt(validation.getMin())>Integer.parseInt(pd.getString(entry.getKey()))){
					result.put(entry.getKey(), "低于最小值");
				}
				if(Tools.notEmpty(validation.getMax())&&Integer.parseInt(validation.getMax())<Integer.parseInt(pd.getString(entry.getKey()))){
					result.put(entry.getKey(), "高于最大值");
				}
				if(validation.getEnumeration().size()>0){
					if(null==validation.getEnumeration().get(pd.getString(entry.getKey()))){
						result.put(entry.getKey(), "不属于规定值");
					}
				}
				if(result.size()==0&&Tools.notEmpty(validation.getDefaultValue())&&Tools.isEmpty(pd.getString(entry.getKey()))){
					pd.put(entry.getKey(),validation.getDefaultValue());
				}
			}   
		}
		
		return result;
	}
	
	
	/**
	 * 设置分页的参数
	 * @param pd
	 * @return
	 */
	public static PageData setPageParam(PageData pd){
		String page_now_str = pd.get("page_now").toString();
		int pageNowInt = Integer.parseInt(page_now_str)-1;
		String page_size_str = pd.get("page_size").toString(); //每页显示记录数
		int pageSizeInt = Integer.parseInt(page_size_str);
		
		String page_now = pageNowInt+"";
		String page_start = (pageNowInt*pageSizeInt)+"";
		
		pd.put("page_now", page_now);
		pd.put("page_start", page_start);
		
		return pd;
	}
	
	/**
	 * 设置list中的distance
	 */
	public static List<PageData>  setListDistance(List<PageData> list, PageData pd){
		List<PageData> listReturn = new ArrayList<PageData>();
		String user_longitude = "";
		String user_latitude = "";
		
		try{
			user_longitude = pd.get("user_longitude").toString(); //"117.11811";
			user_latitude  = pd.get("user_latitude").toString();  //"36.68484";
		} catch(Exception e){
			logger.error("缺失参数--user_longitude和user_longitude");
			logger.error("lost param：user_longitude and user_longitude");
		}
		
		PageData pdTemp = new PageData();
		int size = list.size();
		for(int i=0;i<size;i++){
			pdTemp = list.get(i);
			String longitude = pdTemp.get("longitude").toString();
			String latitude = pdTemp.get("latitude").toString();
			String distance = MapDistance.getDistance(
						user_longitude,	user_latitude,
						longitude,		latitude
					);
			pdTemp.put("distance", distance);
			pdTemp.put("size", distance.length());
			listReturn.add(pdTemp);
		}
		
		return listReturn;
	}
	
	public static Object returnObject(PageData pd, Map map){
		if(pd.containsKey("callback")){
			String callback = pd.get("callback").toString();
			
			//System.out.println("testJSON:"+callback);
			
			return new JSONPObject(callback, map);
		}else{
			return map;
		}
	}
}
