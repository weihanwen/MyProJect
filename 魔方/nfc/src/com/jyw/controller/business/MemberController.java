package com.jyw.controller.business;

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
import com.jyw.service.business.MemberService;
import com.jyw.util.Const;
import com.jyw.util.PageData;

/** 
 * 类名称：WxmemberController
 * 创建人：魏汉文
 * 创建时间：2017-07-28
 */
@Controller
@RequestMapping(value="/member")
public class  MemberController extends BaseController {
	
	@Resource(name="memberService")
	private MemberService memberService;
	
	 
	
	 
	/**
	 * 列表
	 * member/list.do
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = memberService.list(page);	//列出W列表
 			this.getHC(); //调用权限
			mv.setViewName("business/member/member_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
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

