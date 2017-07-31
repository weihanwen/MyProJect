package com.jyw.controller.business;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jyw.controller.base.BaseController;
import com.jyw.entity.Page;
import com.jyw.entity.system.Menu;
import com.jyw.entity.system.User;
import com.jyw.service.business.Weekmeal_cardService;
import com.jyw.util.Const;
import com.jyw.util.PageData;
import com.jyw.util.ServiceHelper;

/** 
 * 类名称：weekmeal_cardController
 * 创建人：魏汉文
 * 创建时间：2017-07-28
 */
@Controller
@RequestMapping(value="/weekmeal_card")
public class Weekmeal_cardController extends BaseController {
	
	@Resource(name="weekmeal_cardService")
	private Weekmeal_cardService weekmeal_cardService;
	
	/**
	 * 新增
	 * weekmeal_card/save.do
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
 		ModelAndView mv = this.getModelAndView();
 		//shiro管理的session
 		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();
 		User user=(User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if(user != null){
				String send_oprator_id=user.getUSER_ID();
				pd.put("create_oprator_id", send_oprator_id);
				weekmeal_cardService.save(pd);
 			}
 		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
 		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 改变是否启用状态
	 * weekmeal_card/changeStatus.do
	 */
	@RequestMapping(value="/changeStatus")
	public void changeStatus(PrintWriter out){
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			weekmeal_cardService.changeStatus(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	
	 
	/**
	 * 提货券列表
	 * weekmeal_card/list.do
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = weekmeal_cardService.list(page);	//列出W列表
			for (PageData e : varList) {
				String receive_condition_namestr="";
				String receive_condition_idstr=e.getString("receive_condition_idstr");
				String[] str=receive_condition_idstr.split(",");
				for (int i = 0; i < str.length; i++) {
					pd.put("receive_condition_id", str[i]);
					receive_condition_namestr+=weekmeal_cardService.findByIdCondition(pd).getString("content")+",";
 				}
				e.put("receive_condition_namestr", receive_condition_namestr);
			}
			pd.remove("receive_condition_id");
			this.getHC(); //调用权限
			mv.setViewName("business/weekmeal_card/weekmeal_card_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 * weekmeal_card/goAdd.do
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
 			pd = this.getPageData();
 			//获取地址
			List<PageData> addressList=ServiceHelper.getAddressService().listAll(pd);
			mv.addObject("addressList", addressList);
 			//获取条件列表
			List<PageData> conditionList=weekmeal_cardService.listReceiveCondition(pd);
			mv.addObject("conditionList", conditionList);
			mv.setViewName("business/weekmeal_card/weekmeal_card_save");
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

