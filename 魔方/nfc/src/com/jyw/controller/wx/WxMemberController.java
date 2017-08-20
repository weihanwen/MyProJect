package com.jyw.controller.wx;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jyw.controller.base.BaseController;
import com.jyw.entity.wx.WxLogin;
import com.jyw.service.business.Carousel_figureService;
import com.jyw.service.business.CategoryService;
import com.jyw.service.business.Daily_menuService;
import com.jyw.service.business.Delivery_feeService;
import com.jyw.service.business.LunchService;
import com.jyw.service.business.Scheduled_timeService;
import com.jyw.service.wx.WxOrderService;
import com.jyw.service.wx.WxmemberService;
import com.jyw.util.Const;
import com.jyw.util.DateUtil;
import com.jyw.util.PageData;
import com.jyw.util.wxpay.WXPayConstants;
import com.jyw.util.wxpay.WXPayPath;
import com.jyw.util.wxpay.WXPayUtil;
 
 
/** 
 * 
* 类名称：HtmlMemberController   
* 类描述：  h5的页面
* 创建人：魏汉文  
* 创建时间：2016年5月26日 下午3:46:49
 */
@Controller("wxMemberController")
@RequestMapping(value="/wxmember")
public class WxMemberController extends BaseController {
	
	

	@Resource(name="wxmemberService")
	private WxmemberService wxmemberService;
	@Resource(name="categoryService")
	private CategoryService categoryService;//类别集合
	@Resource(name="scheduled_timeService")
	private Scheduled_timeService scheduled_timeService;//预定商品列表
	@Resource(name="lunchService")
	private LunchService lunchService;//商品列表
	@Resource(name="daily_menuService")
	private Daily_menuService daily_menuService;//正常商品菜品
	@Resource(name="carousel_figureService")
	private Carousel_figureService carousel_figureService;//轮播图
	@Resource(name="wxOrderService")
	private WxOrderService WxOrderService;//订单
	@Resource(name="delivery_feeService")
	private Delivery_feeService delivery_feeService;//配送费
	
	
	/**
   	 * 获取商品列表
   	* wxmember/getLunchList.do
   	* 
   	* category_id  类别ID
    */
	@RequestMapping(value="/getLunchList")
	@ResponseBody
	public  Object getLunchList(String category_id,String order_type) throws Exception{
 		Map<String, Object> map = new HashMap<String, Object>();
 	  	String result="1";
		String message="获取成功";
		PageData pd=new PageData();
		try{
 			pd.put("category_id", category_id);
 			pd.put("order_type", order_type);
			if(order_type.equals("2")){
    			//判断今天是否设有预定时间
    			pd.put("day", DateUtil.getAfterDayDate(DateUtil.getDay(), "1"));
      			PageData daypd=scheduled_timeService.findByNowDay(pd); 
      			if(daypd != null){
      				long b1=(long) daypd.get("b1");
      				long b2=(long) daypd.get("b2");
      				if(b1>0 && b2>0){
      					List<PageData> ydList=scheduled_timeService.listAllNowDay(pd);
      					map.put("data", ydList);
      					map.put("flag", "true");
      				}else{
      					map.put("data", new ArrayList<PageData>());
      					map.put("flag", "false");
      				}
       			}else{
      				map.put("data", new ArrayList<PageData>());
      			}
    		}else{
     			//3默认获取当日便当类别的所有
     			pd.put("day", DateUtil.getDay());
     			List<PageData> biandanList=daily_menuService.listAllNowDay(pd);
    			map.put("data", biandanList);
    		}
		}catch(Exception e){
			result="0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
 	}
	
	
	
	/**
	 * 前往个人中心
	 * wxmember/gome.do 
	 * 
	 *  返回值：用户信息  
	 *  session存储 Wxlogin  
 	 */
	@RequestMapping(value="/gome")
	public ModelAndView gome()throws Exception{
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
 		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();
 		WxLogin login=(WxLogin) session.getAttribute(Const.WXLOGIN);
  		PageData pd = new PageData();
    	try {
    		if(login != null){
    			pd.put("wxmember_id", login.getWXMEMBER_ID());
        		pd.put("image_url", login.getIMAGE_URL());
        		pd.put("name", login.getNAME());
        		pd.put("showlook_id", login.getSHOWLOOK_ID());
        		pd.put("isuse", "0");
        		pd.put("isfrozen", "0");
        		//获取优惠券个数
        		String countYh=wxmemberService.getRedPackageNumber(pd);
        		//获取提货券个数
        		String countTh=wxmemberService.getRedPackageNumber(pd);
        		//获取积分余额
        		String now_integral=wxmemberService.getNowIntegral(pd);
        		pd.put("countYh", countYh);
        		pd.put("countTh", countTh);
        		pd.put("now_integral", now_integral);
        		pd.remove("isuse");
        		pd.remove("wxmember_id");
        		mv.addObject("pd", pd);
    			mv.setViewName("wx/wxme");
    		}else{
    			mv.setViewName("redirect:../wxlogin/toLoginWx.do");
    		}
        } catch (Exception e) {
   			e.printStackTrace();
 		}
  		return mv;
	}
	
	/**
	 * 前往个人详情页面
	 * wxmember/gomedetail.do 
	 * 
	 *  返回值：用户信息  
	 *  session存储 Wxlogin  
 	 */
	@RequestMapping(value="/gomedetail")
	public ModelAndView gomedetail()throws Exception{
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
 		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();
 		WxLogin login=(WxLogin) session.getAttribute(Const.WXLOGIN);
  		PageData pd = new PageData();
    	try {
    		if(login != null){
         		pd.put("image_url", login.getIMAGE_URL());
        		pd.put("name", login.getNAME());
        		pd.put("showlook_id", login.getSHOWLOOK_ID());
         		mv.addObject("pd", pd);
    			mv.setViewName("wx/wxmedetail");
    		}else{
    			mv.setViewName("redirect:../wxlogin/toLoginWx.do");
    		}
        } catch (Exception e) {
   			e.printStackTrace();
 		}
  		return mv;
	}
	
	
	
	/**
	 * 前往订餐界面
	 * wxmember/wxindex.do 
	 * 
  	 */
	@RequestMapping(value="/wxindex")
	public ModelAndView wxindex()throws Exception{
		ModelAndView mv = this.getModelAndView();
  		PageData pd = new PageData();
    	try {
    		pd=this.getPageData();
    		//1.获取轮播图
 			List<PageData> lunboList=carousel_figureService.listAllOk(pd);
 			mv.addObject("lunboList", lunboList);
 			//2.获取分类类别
 			List<PageData> leibieList=categoryService.listAll(pd);
 			mv.addObject("leibieList", leibieList);
   			mv.setViewName("wx/wxindex");
        } catch (Exception e) {
   			e.printStackTrace();
 		}
  		return mv;
	}
	
	
	/**
	 * 前往预定界面
	 * wxmember/yuding.do 
	 * 
  	 */
	@RequestMapping(value="/yuding")
	public ModelAndView yuding()throws Exception{
		ModelAndView mv = this.getModelAndView();
  		PageData pd = new PageData();
    	try {
    		pd=this.getPageData();
    		//2.获取分类类别
 			List<PageData> leibieList=categoryService.listAll(pd);
 			mv.addObject("leibieList", leibieList);
 			//3默认获取明天可预定的便当类别的所有
 			pd.put("day", DateUtil.getAfterDayDate(DateUtil.getDay(), "1"));
  			PageData daypd=scheduled_timeService.findByNowDay(pd);//获取今天预定的详情
  			if(daypd != null){
  				daypd.put("week", DateUtil.getAfterDayWeek("1"));
  	 			mv.addObject("daypd", daypd);
  			}
   			mv.setViewName("wx/yuding");
        } catch (Exception e) {
   			e.printStackTrace();
 		}
  		return mv;
	}
	
	
	/**
	 * 前往商品的详情页
	 * wxmember/godetailBygoods.do 
	 * 
	 * lunch_id 商品ID
	 * category_id 类别ID
	 * order_type :  1-订单进入，2-预定进入
   	 */
	@RequestMapping(value="/godetailBygoods")
	public ModelAndView godetailBygoods(String lunch_id,String order_type,String category_id)throws Exception{
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
 		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();
 		WxLogin login=(WxLogin) session.getAttribute(Const.WXLOGIN);
  		PageData pd = new PageData();
    	try {
    		if(login != null ){
    			//获取商品详情
        		pd.put("category_id", category_id);
        		pd.put("order_type", order_type);
        		if(order_type.equals("2")){
        			//判断今天是否设有预定时间
        			pd.put("day", DateUtil.getAfterDayDate(DateUtil.getDay(), "1"));
          			PageData daypd=scheduled_timeService.findByNowDay(pd); 
          			if(daypd != null){
          				long b1=(long) daypd.get("b1");
          				long b2=(long) daypd.get("b2");
          				if(b1>0 && b2>0){
          					List<PageData> ydList=scheduled_timeService.listAllNowDay(pd);
          					mv.addObject("varList", ydList);
           				}else{
          					mv.addObject("varList", new ArrayList<PageData>());
           				}
            	 		mv.setViewName("wx/wxgoodsdetail");
          			}else{
          				mv.setViewName("redirect:yuding.do");
          			}
        		}else{
         			//3默认获取当日便当类别的所有
         			pd.put("day", DateUtil.getDay());
         			List<PageData> biandanList=daily_menuService.listAllNowDay(pd);
         			mv.addObject("varList", biandanList); 
         			mv.setViewName("wx/wxgoodsdetail");
        		}
        		pd.put("lunch_id", lunch_id);
        		//获取购物车数量
        		pd.put("wxmember_id", login.getWXMEMBER_ID());
        		pd.put("shopnumber", wxmemberService.countShopcartNumber(pd));
        		pd.remove("wxmember_id");
    		}else{
    			mv.setViewName("redirect:../wxlogin/toLoginWx.do");
    		}
         } catch (Exception e) {
   			e.printStackTrace();
 		}
    	mv.addObject("pd", pd); 
  		return mv;
	}
	
	
	
	/**
   	 * 添加金额购物车
   	* wxmember/addshopcart.do
   	* 
   	* lunch_id  商品ID
   	* number  加1减1
   	 */
	@RequestMapping(value="/addshopcart")
	@ResponseBody
	public  Object addshopcart(HttpServletRequest request,String lunch_id,String number) throws Exception{
 		Map<String, Object> map = new HashMap<String, Object>();
 		//shiro管理的session
 		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();
 		WxLogin login=(WxLogin) session.getAttribute(Const.WXLOGIN);
	  	String result="1";
		String message="添加成功";
		PageData pd=new PageData();
		try{
			if(login == null){
				result="0";
				message="登录身份失效，请重新登录";
			}else{
				pd.put("wxmember_id", login.getWXMEMBER_ID()) ;
				pd.put("lunch_id", lunch_id);
				boolean flag=true;
				if(number.equals("1")){
					flag=isKunCunOK(lunch_id, number, "1");
				}
 				if(flag){
					//判断购物车是否有当前的商品
					PageData shoppd=wxmemberService.findShopCartById(pd);
					if(shoppd == null){
						String shopcart_id=BaseController.get10UID();
						pd.put("shopcart_id", shopcart_id);
						wxmemberService.saveShopCartById(pd);
					}else{
						if(shoppd.getString("shop_number").equals("1") && number.equals("-1")){
							wxmemberService.deleteShopCartById(shoppd);
						}else{
 							shoppd.put("number", number);
							wxmemberService.updateShopCartById(shoppd);
	 					}
					}
				}else{
					result="0";
					message="库存不足";
				}
				
				map.put("data", wxmemberService.countShopcartNumber(pd));
  			}
 		}catch(Exception e){
			result="0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
 	}

	/**
	 * 前往购物车列表
	 * wxmember/goshopcatList.do 
     */
	@RequestMapping(value="/goshopcatList")
	public ModelAndView goshopcatList()throws Exception{
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
 		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();
 		WxLogin login=(WxLogin) session.getAttribute(Const.WXLOGIN);
  		PageData pd = new PageData();
    	try {
    		if(login != null ){
    			pd.put("wxmember_id", login.getWXMEMBER_ID());
    			//获取购物车列表
    			List<PageData> shopList=wxmemberService.findShopCartList(pd);
    			mv.addObject("shopList", shopList);
     			mv.setViewName("wx/shopcartlist");
     		}else{
    			mv.setViewName("redirect:../wxlogin/toLoginWx.do");
    		}
         } catch (Exception e) {
   			e.printStackTrace();
 		}
    	mv.addObject("pd", pd); 
  		return mv;
	}
	/**
	 * 计算总价
	 * wxmember/countAllShopMoney.do
	 * 
	 * allshopcart_id  商品所有ID集合
 	 */
	@RequestMapping(value="/countAllShopMoney")
	@ResponseBody
	public  Object countAllShopMoney(String allshopcart_id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
 		String result="1";
		String message="统计完成";
		PageData pd=new PageData();
		try{
			pd.put("allshopcart_id", allshopcart_id);
			map.put("data", wxmemberService.sumShopcartById(pd));
 		}catch(Exception e){
			result="0";
			message="系统异常";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	/**
	 * 去支付界面
	 * wxmember/goPayJSP.do?shop_type=1&allshopcart_id=&order_type=1&lunch_idstr=&wxmember_address_id=&wxmember_redpackage_id=&wxmember_tihuojuan_idstr=
	 * 
	 * shop_type 		1-购物车购买，2-直接购买
 	 * allshopcart_id  	购物车结算嘚所有购物车ID
	 * lunch_idstr   	ID@数量
	 * order_type  		1-点餐，2-预定
	 * 
	 * 二次加载可能嘚参数
	 * wxmember_address_id  		地址id
	 * wxmember_redpackage_id  		使用红包id
	 * wxmember_tihuojuan_idstr		使用嘚提货卷ID
	 * 
      */
	@RequestMapping(value="/goPayJSP")
	public ModelAndView goPayJSP()throws Exception{
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
 		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();
 		WxLogin login=(WxLogin) session.getAttribute(Const.WXLOGIN);
  		PageData pd = new PageData();
    	try {
    		pd=this.getPageData();
    		if(login != null ){
    			pd.put("wxmember_id", login.getWXMEMBER_ID());
    			mv.addObject("nowintegral", wxmemberService.getNowIntegral(pd));
    			String shop_type=pd.getString("shop_type");
    			int allmoney=0;
    			int delivery_fee=0;
    			if(shop_type.equals("1")){
    				//获取购买商品列表
        			List<PageData> shopList=wxmemberService.findShopCartList(pd);
        			mv.addObject("shopList", shopList);
        			//获取总金额
        			String allpaymoney=wxmemberService.sumShopcartById(pd);
        			allmoney=Integer.parseInt(allpaymoney);
        			//配送费
        			delivery_fee=Integer.parseInt(delivery_feeService.getMoneyByNumber(wxmemberService.countShopcartNumber(pd)) );
    				mv.addObject("delivery_fee", String.valueOf(delivery_fee));
    			}else{
    				String lunch_id=pd.getString("lunch_idstr").split("@")[0];
    				pd.put("lunch_id", lunch_id);
    				String shop_number=pd.getString("lunch_idstr").split("@")[1];
    				//获取商品详情
    				PageData lunchpd=lunchService.findByIdForWx(pd);
    				lunchpd.put("shop_number", shop_number);
    				allmoney=Integer.parseInt(lunchpd.get("sale_money").toString())*Integer.parseInt(shop_number);
    				lunchpd.put("allsale_money", String.valueOf(allmoney));
    				mv.addObject("lunchpd", lunchpd);
     				//配送费
    				delivery_fee=Integer.parseInt(delivery_feeService.getMoneyByNumber(shop_number));
    				mv.addObject("delivery_fee", String.valueOf(delivery_fee));
    			}
    			mv.addObject("allmoney", allmoney);
    			int lesspaymoney=allmoney+delivery_fee;
    			int  discount_money=0;
     			//判断是否使用提货卷
     			if(pd.getString("wxmember_tihuojuan_idstr") != null &&  !pd.getString("wxmember_tihuojuan_idstr").equals("")){
     				String[] thjstr=pd.getString("wxmember_tihuojuan_idstr").split(",");
      				for (int i = 0; i < thjstr.length; i++) {
						pd.put("wxmember_tihuojuan_id", thjstr[i]);
						discount_money+=Integer.parseInt(wxmemberService.getTiHuoJuanMoneyById(pd));
					}
     				pd.remove("wxmember_tihuojuan_id");
     				pd.put("wxmember_redpackage_id", "");
      			}else{
     				//判断是否使用红包
         			if(pd.getString("wxmember_redpackage_id") != null && !pd.getString("wxmember_redpackage_id").equals("")){
         				mv.addObject("redmoney", wxmemberService.getRedPackageMoneyById(pd)+"元红包");
         				discount_money+=Integer.parseInt(wxmemberService.getRedPackageMoneyById(pd));
        			}
     			}
     			lesspaymoney=lesspaymoney-discount_money;
    			mv.addObject("discount_money", discount_money);
    			mv.addObject("actual_money", lesspaymoney);
    			//判断是否有选择地址
    			if(pd.getString("wxmember_address_id") != null && !pd.getString("wxmember_address_id").equals("") ){
    				mv.addObject("address", wxmemberService.findAddressDetail(pd).getString("address"));
    			}
    			//设置时间
    			if(pd.getString("order_type").equals("1")){
    				mv.addObject("reserve_arrival_time", "14:22");
    			}else{
    				
    			}
     			
    			
     			mv.setViewName("wx/orderpay");
     		}else{
    			mv.setViewName("redirect:../wxlogin/toLoginWx.do");
    		}
         } catch (Exception e) {
   			e.printStackTrace();
 		}
    	mv.addObject("pd", pd); 
  		return mv;
	}
	

	/**
	 * 去选择地址页面
	 * wxmember/changeAddress.do 
	 * 
	 * shop_type 		1-购物车购买，2-直接购买
 	 * allshopcart_id  	购物车结算嘚所有购物车ID
	 * lunch_idstr   	ID@数量
	 * order_type  		1-点餐，2-预定
	 * 
	 * 二次加载可能嘚参数
	 * wxmember_address_id  		地址id
	 * wxmember_redpackage_id  		使用红包id
	 * wxmember_tihuojuan_idstr		使用嘚提货卷ID *  
 	 */
	@RequestMapping(value="/changeAddress")
	public ModelAndView changeAddress()throws Exception{
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
 		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();
 		WxLogin login=(WxLogin) session.getAttribute(Const.WXLOGIN);
  		PageData pd = new PageData();
    	try {
    		pd=this.getPageData();
    		if(login != null){
         		pd.put("wxmember_id", login.getWXMEMBER_ID());
        		List<PageData> addressList=wxmemberService.getMyAddressList(pd);
        		mv.addObject("addressList", addressList);
        		pd.remove("wxmember_id");
         		mv.addObject("pd", pd);
    			mv.setViewName("wx/usered");
    		}else{
    			mv.setViewName("redirect:../wxlogin/toLoginWx.do");
    		}
        } catch (Exception e) {
   			e.printStackTrace();
 		}
  		return mv;
	}
	

	/**
	 * 去餐盒说明界面
	 * wxmember/canheDetail.do 
	 * 
	 * wxmember_address_id  		地址id
 	 */
	@RequestMapping(value="/canheDetail")
	public ModelAndView canheDetail()throws Exception{
		ModelAndView mv = this.getModelAndView();
   		PageData pd = new PageData();
    	try {
    		//获取同个地址得正在订餐得同事：根据地址检索，今天正在配送订单得同事
    		
    		//获取所有成功订餐得同事：按时间排序，最近得10份订单得，已完成得
    		
    		mv.addObject("pd", pd);
			mv.setViewName("wx/canhedetail");
        } catch (Exception e) {
   			e.printStackTrace();
 		}
  		return mv;
	}
	
	
	
	/**
	 * 去使用红包界面
	 * wxmember/gouserRed.do 
	 * 
	 * shop_type 		1-购物车购买，2-直接购买
 	 * allshopcart_id  	购物车结算嘚所有购物车ID
	 * lunch_idstr   	ID@数量
	 * order_type  		1-点餐，2-预定
	 * 
	 * 二次加载可能嘚参数
	 * wxmember_address_id  		地址id
	 * wxmember_redpackage_id  		使用红包id
	 * wxmember_tihuojuan_idstr		使用嘚提货卷ID *  
 	 */
	@RequestMapping(value="/gouserRed")
	public ModelAndView gouserRed()throws Exception{
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
 		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();
 		WxLogin login=(WxLogin) session.getAttribute(Const.WXLOGIN);
  		PageData pd = new PageData();
    	try {
    		pd=this.getPageData();
    		if(login != null){
         		pd.put("wxmember_id", login.getWXMEMBER_ID());
        		List<PageData> redList=wxmemberService.getMyRedList(pd);
        		mv.addObject("redList", redList);
        		pd.remove("wxmember_id");
         		mv.addObject("pd", pd);
    			mv.setViewName("wx/usered");
    		}else{
    			mv.setViewName("redirect:../wxlogin/toLoginWx.do");
    		}
        } catch (Exception e) {
   			e.printStackTrace();
 		}
  		return mv;
	}
	
	

	/**
	 * 去使用提货卷界面
	 * wxmember/gouserTiHuoJuan.do 
	 * 
	 * shop_type 		1-购物车购买，2-直接购买
 	 * allshopcart_id  	购物车结算嘚所有购物车ID
	 * lunch_idstr   	ID@数量
	 * order_type  		1-点餐，2-预定
	 * 
	 * 二次加载可能嘚参数
	 * wxmember_address_id  		地址id
	 * wxmember_redpackage_id  		使用红包id
	 * wxmember_tihuojuan_idstr		使用嘚提货卷ID *  
 	 */
	@RequestMapping(value="/gouserTiHuoJuan")
	public ModelAndView gouserTiHuoJuan()throws Exception{
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
 		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();
 		WxLogin login=(WxLogin) session.getAttribute(Const.WXLOGIN);
  		PageData pd = new PageData();
    	try { 
    		pd=this.getPageData();
    		if(login != null){
         		pd.put("wxmember_id", login.getWXMEMBER_ID());
        		List<PageData> tihuoredList=wxmemberService.getMyTiHuoList(pd);
        		for (PageData e : tihuoredList) {
//        			System.out.println(Double.parseDouble(pd.getString("allmoney"))-Double.parseDouble(e.get("money").toString()));
					if(Double.parseDouble(pd.getString("allmoney"))-Double.parseDouble(e.get("money").toString()) >=0){
						e.put("isok", "1");
					}else{
						e.put("isok", "0");
					}
				}
        		mv.addObject("tihuoredList", tihuoredList);
        		mv.addObject("number", tihuoredList.size()  );
        		pd.remove("wxmember_id");
         		mv.addObject("pd", pd);
    			mv.setViewName("wx/usetihuojuan");
    		}else{
    			mv.setViewName("redirect:../wxlogin/toLoginWx.do");
    		}
        } catch (Exception e) {
   			e.printStackTrace();
 		}
  		return mv;
	}
	
	
	
	
	/**
   	 * 微信支付的订单交易支付接口
   	* 方法名称:：payorder 
   	* 方法描述：订单支付接口
   	* html_member/payorder.do
   	* 
	 * shop_type 		1-购物车购买，2-直接购买
 	 * allshopcart_id  	购物车结算嘚所有购物车ID
	 * lunch_idstr   	ID@数量
	 * order_type       1-点餐购买，2-预定购买
   	* 
   	 */
	@RequestMapping(value="/payorder")
	@ResponseBody
	public  Object PayOrder(HttpServletRequest request) throws Exception{
 		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		//shiro管理的session
 		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();
 		WxLogin login=(WxLogin) session.getAttribute(Const.WXLOGIN);
	  	String result="1";
		String message="支付成功";
		PageData pd=new PageData();
		try{
			pd = this.getPageData();
 			if(login == null){
 				map.put("result", "0");
				map.put("message", "请先前往登录。。。");
				map.put("data", "");
				return map;
			}
 			pd.put("wxmember_id", login.getWXMEMBER_ID());
 			String now_integral=wxmemberService.getNowIntegral(pd);
 			String use_integral=pd.getString("use_integral");
 			if(Integer.parseInt(now_integral) < Integer.parseInt(use_integral)){
 				map.put("result", "0");
				map.put("message", "积分不足!");
				map.put("data", "");
				return map;
 			}
 			//设置订单号
			String order_id=BaseController.get12UID();
			//如果是直接购买需要判断库存
			String shop_type=pd.getString("shop_type");
			if(shop_type.equals("2")){
				String lunch_id=pd.getString("lunch_idstr").split("@")[0];
				String number=pd.getString("lunch_idstr").split("@")[1];
				boolean flag=isKunCunOK(lunch_id, number, pd.getString("order_type"));
				if(!flag){
					map.put("result", "0");
					map.put("message", "商品正在补给，请稍等。。。");
					map.put("data", "");
					return map;
				}
				//添加库存嘚定时器-5分钟
				
 			}
 			//获取微信支付嘚信息
			String use_wx=pd.getString("use_wx");
			if(Integer.parseInt(use_wx) > 0){
				//新增微支付完成订单
				
				
				data=WxPayOrder(use_wx, "1", order_id, wxmemberService.findById(pd).getString("open_id"));
				map.put("data", data);
			}else{
				//新增已支付完成订单
				
				data.put("out_trade_no", order_id);
				map.put("data", data);
			}
			//减少会员使用积分
			if(Integer.parseInt(use_integral) > 0){
				
			}			
		}catch(Exception e){
			result="0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;

	}

	
	
	
	
	
	
	/**
	 * 判断当前商品嘚库存是否充足
	 * @param lunch_id 商品ID
	 * @param number 购买嘚数量
	 * @return type 1-点餐，2-预定
	 */
	public synchronized boolean isKunCunOK(String lunch_id,String number,String type){
		boolean flag=true;
		PageData kcpd=new PageData();
		try {
			kcpd.put("lunch_id", lunch_id);
			//获取当前库存以及版本号比对库存
			kcpd=lunchService.findByIdForKunCun(kcpd);
			if(kcpd == null){
				return false;
			}
			if(type.equals("1")){
				String dc_stocknumber=kcpd.getString("dc_stocknumber");
				if(Integer.parseInt(dc_stocknumber) < Integer.parseInt(number)){
					return false;
				}
				PageData newpd=new PageData();
				newpd.put("lunch_id", lunch_id);
 				String dc_version=kcpd.getString("dc_version");
 				newpd.put("dc_stocknumber", String.valueOf(Integer.parseInt(dc_stocknumber)-Integer.parseInt(number)));
  				if(number.equals("1")){
   					newpd.put("dc_version", String.valueOf(Integer.parseInt(dc_version)+1));
   					if(lunchService.findByIdForKunCun(kcpd).getString("dc_version").equals(newpd.getString("dc_version"))){
  						return false;
  					}
 				}
 				lunchService.editStock(newpd);
 			}else{
// 				String yd_stocknumber=kcpd.getString("yd_stocknumber");
//				if(Integer.parseInt(yd_stocknumber) < Integer.parseInt(number)){
//					return false;
//				}
//				String yd_version=kcpd.getString("yd_version");
//  				PageData newpd=new PageData();
//				newpd.put("yd_stocknumber", String.valueOf(Integer.parseInt(yd_stocknumber)-Integer.parseInt(number)));
// 				if(number.equals("1")){
//					newpd.put("yd_version", String.valueOf(Integer.parseInt(yd_version)+1));
//					newpd.put("lunch_id", lunch_id);
//					if(lunchService.findByIdForKunCun(kcpd).getString("yd_version").equals(newpd.getString("yd_version"))){
//						return false;
//					}
//				}
// 				lunchService.editStock(newpd);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
  		return flag;
  	}
	
	/**
	 * 公众号微信支付
	 * html_member/wxpayorder.do?total_fee=0.01&attach=1&body=购买商品
	 * 
	 * total_fee  金额
	 * attach     支付类型  1-支付，2-充值
	 * body       商品说明
	 * out_trade_no   订单ID
	 */
	public static Map<String, String> WxPayOrder(String _total_fee,String attach,String out_trade_no,String openid) throws Exception{
		Map<String, String> returnmap=new HashMap<String, String>();
   		try {
    		BigDecimal total_fee = new BigDecimal(Double.parseDouble(_total_fee)*100);
  	    	//开始调用微信支付接口
  			WXPayPath dodo = new WXPayPath("3");
 	    	Map<String, String> reqData=new HashMap<String, String>();
 	    	if(attach.equals("1")){
 	    		reqData.put("body", "支付订单");
 	    	}else{
 	    		reqData.put("body", "充值积分");
 	    	}
  	    	reqData.put("attach",attach);
 	    	reqData.put("out_trade_no", out_trade_no);
	    	reqData.put("fee_type", "CNY");
	    	reqData.put("total_fee", String.valueOf(total_fee.intValue()));
	    	reqData.put("spbill_create_ip", dodo.getSpbill_create_ip());
	    	reqData.put("notify_url", "https://www.jiuyuvip.com/back_chat/notify.do");
	     	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
	    	//MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
	    	reqData.put("trade_type", "JSAPI");
	    	reqData.put("openid", openid);
	        reqData.put("sign_type", WXPayConstants.MD5);
  	    	Map<String, String> map2=dodo.unifiedOrder(reqData);
 	    	//开始处理结果
  	        if(map2.get("return_code").toString().equals("SUCCESS") && map2.get("result_code").toString().equals("SUCCESS")  ){
  	    	  returnmap.put("appId", map2.get("appid").toString());
 	    	  returnmap.put("timeStamp", String.valueOf(WXPayUtil.getCurrentTimestamp()));
 	    	  returnmap.put("nonceStr", WXPayUtil.generateNonceStr());
 	    	  returnmap.put("package","prepay_id="+ map2.get("prepay_id").toString());
 	    	  returnmap.put("signType", WXPayConstants.MD5);
   	    	 //二次签名
   	    	  String sign=dodo.AddSign(returnmap);
   	    	  returnmap=WXPayUtil.xmlToMap(sign);
     	      returnmap.put("result_code", map2.get("result_code").toString());
  	       } 
  	       returnmap.put("payment_type", attach);
  	       returnmap.put("out_trade_no", out_trade_no);
  	       returnmap.put("return_code", map2.get("return_code").toString());
	       returnmap.put("return_msg", map2.get("return_msg").toString());
   		} catch (Exception e) {
			// TODO: handle exception
 			e.printStackTrace();
		}
		return returnmap;
	}
	
	
	

   
	
	
 
	
	
	
}
