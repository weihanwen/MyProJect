package com.jyw.controller.business;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.jyw.service.business.OrderService;
import com.jyw.util.Const;
import com.jyw.util.PageData;
import com.jyw.util.ServiceHelper;
import com.jyw.util.file.ObjectExcelView;

/** 
 * 类名称：orderController
 * 创建人：魏汉文
 * 创建时间：2017-07-28
 */
@Controller
@RequestMapping(value="/order")
public class OrderController extends BaseController {
	
	@Resource(name="orderService")
	private OrderService orderService;
	
	 
	
	 
	/**
	 * 列表
	 * order/list.do
	 * 
	 * 必传order_status
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			List<PageData> addressList=ServiceHelper.getAddressService().listAll(pd);
			mv.addObject("addressList", addressList);
			pd = this.getPageData();
 			String lunch_name=pd.getString("lunch_name");
			if(lunch_name != null && !lunch_name.equals("")){
				String order_idstr=orderService.getOrderStrByLunchName(pd);
				pd.put("order_idstr", order_idstr);
			}
			page.setPd(pd);
			List<PageData>	varList = orderService.list(page);
			for (PageData e : varList) {
				e.put("lunchList", orderService.listLunchByOrder(e));
			}
 			this.getHC(); //调用权限
 			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			if(pd.getString("order_status").equals("1")){
				mv.setViewName("business/order/order_list1");
			}else{
				mv.setViewName("business/order/order_list2");
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	 
	/**
	 * 详情
	 * order/goDetail.do
	 */
	@RequestMapping(value="/goDetail")
	public ModelAndView goDetail() throws Exception{
 		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd=orderService.findById(pd);
			mv.addObject("lunchList", orderService.listLunchByOrder(pd));
			mv.setViewName("business/order/order_detail");
 			mv.addObject("pd", pd);
 		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
 		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	

	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
 		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("w");	 
			dataMap.put("titles", titles);
			List<PageData> varOList = orderService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("W"));	//1
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
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

