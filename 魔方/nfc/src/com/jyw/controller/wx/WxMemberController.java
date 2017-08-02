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
import com.jyw.service.business.LunchService;
import com.jyw.service.business.Scheduled_timeService;
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
       	 			List<PageData> ydList=scheduled_timeService.listAllNowDay(pd);
       				map.put("data", ydList);
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
  		PageData pd = new PageData();
    	try {
     		//获取商品详情
    		pd.put("category_id", category_id);
    		pd.put("order_type", order_type);
    		if(order_type.equals("2")){
    			//判断今天是否设有预定时间
    			pd.put("day", DateUtil.getAfterDayDate(DateUtil.getDay(), "1"));
      			PageData daypd=scheduled_timeService.findByNowDay(pd); 
      			if(daypd != null){
       	 			List<PageData> ydList=scheduled_timeService.listAllNowDay(daypd);
       	 			mv.addObject("varList", ydList); 
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
				//判断购物车是否有当前的商品
				PageData shoppd=wxmemberService.findShopCartById(pd);
				if(shoppd == null){
					wxmemberService.saveShopCartById(pd);
				}else{
					if(shoppd.getString("shop_number").equals("1")){
						wxmemberService.deleteShopCartById(shoppd);
					}else{
						shoppd.put("number", number);
						wxmemberService.updateShopCartById(shoppd);
					}
				}
 			}
 		}catch(Exception e){
			result="0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		return map;
 	}
	
	
	
	
	
	
	
	
	
	
	
	
	 
   
	// 开始转微信支付的一些接口操作======================================================================
	
	/**
	 * 公众号微信支付
	 * html_member/wxpayorder.do?total_fee=0.01&attach=1&body=购买商品
	 * 
	 * total_fee  金额
	 * attach     支付类型  1-优惠买单支付，2-购买提货券商品,3-优选商品,4-充值商品
	 * body       商品说明
	 * out_trade_no   订单ID
	 */
	public static Map<String, String> WxPayOrder(String _total_fee,String attach,String out_trade_no) throws Exception{
		Map<String, String> returnmap=new HashMap<String, String>();
   		try {
  			PageData pd=new PageData();
   			BigDecimal total_fee = new BigDecimal(Double.parseDouble(_total_fee)*100);
  	    	//开始调用微信支付接口
  			WXPayPath dodo = new WXPayPath("3");
 	    	Map<String, String> reqData=new HashMap<String, String>();
 	    	reqData.put("body", "九鱼网-充值余额");
 	    	reqData.put("attach",attach);
 	    	reqData.put("out_trade_no", out_trade_no);
	    	reqData.put("fee_type", "CNY");
	    	reqData.put("total_fee", String.valueOf(total_fee.intValue()));
	    	reqData.put("spbill_create_ip", dodo.getSpbill_create_ip());
	    	reqData.put("notify_url", "https://www.jiuyuvip.com/back_chat/notify.do");
	     	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
	    	//MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
	    	reqData.put("trade_type", "JSAPI");
	    	reqData.put("openid", "");
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
	
	
	

   	/**
   	 * 微信支付的订单交易支付接口
   	* 方法名称:：payorder 
   	* 方法描述：订单支付接口
   	* html_member/payorder.do
   	* 
   	 */
	@RequestMapping(value="/payorder")
	@ResponseBody
	public  Object PayOrder(HttpServletRequest request) throws Exception{
 		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
	  	String result="1";
		String message="支付成功";
		PageData pd=new PageData();
		try{
			pd = this.getPageData();
			
		}catch(Exception e){
			result="0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", data);
		return map;

	}

	
	
	
 
	
	
	
}
