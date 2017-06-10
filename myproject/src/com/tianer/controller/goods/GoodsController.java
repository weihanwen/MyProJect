package com.tianer.controller.goods;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.entity.system.Menu;
import com.tianer.entity.wx.Wx_login;
import com.tianer.service.shiyou.ShiYouService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;
import com.tianer.util.rsa.RSACoder;
import com.tianer.util.rsa.RSACoderTest;
import com.tianer.util.wx.WxUtil;
import com.tianer.util.wx.WxpubOAuth;
  
/** 
 * 商品管理层
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/goods")
public class GoodsController extends BaseController {
	
	@Resource(name="shiYouService")
	private ShiYouService shiYouService;
 	
	
	/**
	 * 前往商品列表界面
	 * @return
	 */
	@RequestMapping(value="/goGoodsList")
	public ModelAndView goGoodsList()throws Exception{
		ModelAndView mv = this.getModelAndView();
  		PageData pd = new PageData();
 		try { 
 			
 			pd = this.getPageData();	
 			mv.setViewName("shiyou/shiyou_zhu");
 		} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
				logger.error(e.toString(), e);
 		}
 		return mv;
	}
	
	/**
	 * 前往商品详情界面
	 * @return
	 */
	@RequestMapping(value="/goGoodsDetail")
	public ModelAndView goGoodsDetail()throws Exception{
		ModelAndView mv = this.getModelAndView();
  		PageData pd = new PageData();
 		try { 
  			pd = this.getPageData();	
  			
  			
 			mv.setViewName("shiyou/shiyou_zhu");
 		} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
				logger.error(e.toString(), e);
 		}
 		return mv;
	}
	
	 
	
 
}
