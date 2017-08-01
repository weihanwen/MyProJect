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
 		return mv;
	}
	
	

	/**
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView Bankexcel( ){
 		ModelAndView mv = new ModelAndView();
 		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("订单号");	//1
 			titles.add("菜品");		//2
			titles.add("订单金额");	//3
			titles.add("下单时间");	//4
			titles.add("收货人");	//5
			titles.add("收货电话");	//6
			titles.add("订单类型");	//7
			titles.add("配送地址");	//7
   			dataMap.put("titles", titles);
 			List<PageData> varList = new ArrayList<PageData>();
 			List<PageData> orderList=orderService.listAll(pd);
			for(int i=0;i<orderList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1",orderList.get(i).getString("looknumber"));	//1
 				String lunch_namestr="";
				List<PageData> lunchList=orderService.listLunchByOrder(orderList.get(i));
				for (PageData e : lunchList) {
					lunch_namestr+=e.getString("lunch_name")+"下单"+e.getString("shop_number")+"份";
				}
				vpd.put("var2",lunch_namestr );	//2
				vpd.put("var3", orderList.get(i).getString("allmoney"));	//3
				vpd.put("var4", orderList.get(i).get("createtime"));	//4
				vpd.put("var5", orderList.get(i).getString("contacts"));	//5
				vpd.put("var6", orderList.get(i).getString("contacts_number"));	//6
				if( orderList.get(i).getString("order_type").equals("1")){
					vpd.put("var7","当日订单");	 
				}else{
					vpd.put("var7","预约订单-预订日期"+ orderList.get(i).getString("reserve_day"));	 
				}
				vpd.put("var6", orderList.get(i).getString("address_name")+orderList.get(i).getString("corporate_name")+orderList.get(i).getString("floor_numbe"));	
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
	
	

	/**
	 * 销售明细
	 * order/saleHistory.do
	 * 
	 * 必传order_status
	 */
	@RequestMapping(value="/saleHistory")
	public ModelAndView saleHistory(Page page){
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
			List<PageData>	varList = orderService.saleHistory(page);
			for (PageData e : varList) {
				e.put("lunchList", orderService.listLunchByOrder(e));
			}
			mv.addObject("total", orderService.totalOrder(page));//统计
 			this.getHC(); //调用权限
 			mv.addObject("varList", varList);
 			mv.addObject("pd", pd);
			mv.setViewName("business/order/order_list");
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

