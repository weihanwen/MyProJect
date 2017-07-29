package com.jyw.controller.business;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jyw.controller.base.BaseController;
import com.jyw.entity.Page;
import com.jyw.entity.system.Menu;
import com.jyw.service.business.Carousel_figureService;
import com.jyw.util.AppUtil;
import com.jyw.util.Const;
import com.jyw.util.PageData;
import com.jyw.util.ServiceHelper;
import com.jyw.util.file.ObjectExcelView;

/** 
 * 类名称：Carousel_figureController
 * 创建人：魏汉文
 * 创建时间：2017-07-28
 */
@Controller
@RequestMapping(value="/carousel_figure")
public class Carousel_figureController extends BaseController {
	
	@Resource(name="carousel_figureService")
	private Carousel_figureService carousel_figureService;
	
	/**
	 * 新增
	 * carousel_figure/save.do
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			carousel_figureService.save(pd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
 		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 * carousel_figure/delete.do
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			carousel_figureService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 * carousel_figure/edit.do
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			carousel_figureService.edit(pd);
		} catch (Exception e) {
			// TODO: handle exception
		}
 		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 轮播图列表
	 * carousel_figure/list.do
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = carousel_figureService.list(page);	//列出W列表
			this.getHC(); //调用权限
			mv.setViewName("business/carousel_figure/carousel_figure_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 * carousel_figure/goAdd.do
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
 			pd = this.getPageData();
 			//获取所有上架商品
 			List<PageData> lunchList=ServiceHelper.getLunchService().listAllUpShelves(pd);
 			mv.addObject("lunchList", lunchList);
			mv.setViewName("business/carousel_figure/carousel_figure_save");
 			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
 			pd = this.getPageData();
			//获取所有上架商品
 			List<PageData> lunchList=ServiceHelper.getLunchService().listAllUpShelves(pd);
 			mv.addObject("lunchList", lunchList);
			pd = carousel_figureService.findById(pd);	//根据ID读取
			mv.setViewName("business/carousel_figure/carousel_figure_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	 
	/* ===============================权限================================== */
	public void getHC(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_QX);
		mv.addObject(Const.SESSION_QX,map);	//按钮权限
		List<Menu> menuList = (List)session.getAttribute(Const.SESSION_menuList);
		mv.addObject(Const.SESSION_menuList, menuList);//菜单权限
	}
	/* ===============================权限================================== */
	
 
	
}

