package com.jyw.controller.img;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jyw.controller.base.BaseController;
import com.jyw.util.AppUtil;
import com.jyw.util.PageData;
import com.jyw.util.file.FileUpload;

/** 
 * 类名称：ImageController
 * 创建人：魏汉文
 * 创建时间：2017-07-28
 */
@Controller
@RequestMapping(value="/imgChange")
public class ImageChangeController extends BaseController {


	/**
	 * 修改轮播图
	 * imgChange/editLb.do
 	 */
	@RequestMapping(value="/editLb")
	@ResponseBody
	public Object editLb(
			@RequestParam(value="upImage",required=false) MultipartFile file
  			){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="新增成功";
 		try{
 			String logourl="";
			if(file != null){
 					String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
					String filePath = "/carousel";//文件上传路径
 					String cityFilename =  FileUpload.fileUp(file, currentPath+filePath,this.get32UUID());//字符拼接，上传到服务器上
 					logourl = AppUtil.getuploadRootUrlIp()+filePath+"/"+cityFilename;
    	 	 }else{
   	 			result="0";
   	 			message="上传失败[file]不能为空";
   	 		}
			map.put("data", logourl);
		} catch(Exception e){
			result = "0";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
}

