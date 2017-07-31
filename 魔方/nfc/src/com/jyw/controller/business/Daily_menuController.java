package com.jyw.controller.business;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jyw.controller.base.BaseController;
import com.jyw.entity.Page;
import com.jyw.entity.system.Menu;
import com.jyw.service.business.Daily_menuService;
import com.jyw.util.Const;
import com.jyw.util.PageData;
import com.jyw.util.ServiceHelper;

/** 
 * 类名称：daily_menuController
 * 创建人：魏汉文
 * 创建时间：2017-07-28
 */
@Controller
@RequestMapping(value="/daily_menu")
public class Daily_menuController extends BaseController {
	
	@Resource(name="daily_menuService")
	private Daily_menuService daily_menuService;
	
	/**
	 * 新增
	 * daily_menu/save.do
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			daily_menuService.save(pd);
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
	 * daily_menu/delete.do
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			daily_menuService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
 
	 
	
	/**
	 * 修改
	 * daily_menu/edit.do
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			daily_menuService.edit(pd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
 		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 * daily_menu/list.do
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = daily_menuService.list(page);	//列出W列表
			for (PageData e : varList) {
				String lunch_namestr="";
				String lunch_idstr=e.getString("lunch_idstr");
				String[] str=lunch_idstr.split(",");
				for (int i = 0; i < str.length; i++) {
					pd.put("lunch_id", str[i]);
					lunch_namestr+=ServiceHelper.getLunchService().findById(pd).getString("lunch_name")+",";
 				}
				e.put("lunch_namestr", lunch_namestr);
			}
			pd.remove("lunch_id");
			this.getHC(); //调用权限
			mv.setViewName("business/daily_menu/daily_menu_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 * daily_menu/goAdd.do
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
 			pd = this.getPageData();
 			//获取便当列表
			List<PageData> lunchList=ServiceHelper.getLunchService().listAllUpShelves(pd);
			mv.addObject("lunchList", lunchList);
			mv.setViewName("business/daily_menu/daily_menu_save");
 			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 * daily_menu/goEdit.do
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
 			pd = this.getPageData();
 			//获取便当列表
			List<PageData> lunchList=ServiceHelper.getLunchService().listAllUpShelves(pd);
			mv.addObject("lunchList", lunchList);
			pd = daily_menuService.findById(pd);	//根据ID读取
			mv.addObject("pd", pd);
			mv.setViewName("business/daily_menu/daily_menu_edit");
			mv.addObject("msg", "edit");
			
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

