package com.jyw.service.wx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jyw.dao.DaoSupport;
import com.jyw.entity.Page;
import com.jyw.util.PageData;


@Service("wxmemberService")
public class   WxmemberService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增会员,财富
	*/
	public void save(PageData pd)throws Exception{
		dao.save("WxmemberMapper.save", pd);
		dao.save("WxmemberMapper.saveWealth", pd);
	}
	
	
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("WxmemberMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("WxmemberMapper.edit", pd);
	}
	
	/*
	 * 修改
	 */
	public void changeMoneyByMember(PageData pd)throws Exception{
		dao.update("WxmemberMapper.changeMoneyByMember", pd);
	}
	 
	/*
	* 通过id获取数据-通过openid/wxmember_id
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WxmemberMapper.findById", pd);
	}
  	/*
	* 获取当前会员的余额
	*/
	public String  getNowIntegral(PageData pd)throws Exception{
		return (String)dao.findForObject("WxmemberMapper.getNowIntegral", pd);
	}
	
	
	
	
	/*
	* 获取当前会员的红包个数================================================================================
	*/
	public String  getRedPackageNumber(PageData pd)throws Exception{
		return (String)dao.findForObject("Wxmember_redpackageMapper.getRedPackageNumber", pd);
	}
	public String  getRedPackageMoneyById(PageData pd)throws Exception{
		return (String)dao.findForObject("Wxmember_redpackageMapper.getRedPackageMoneyById", pd);
	}
	public void editRedPackage(PageData pd)throws Exception{
		dao.update("Wxmember_redpackageMapper.editRedPackage", pd);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 获取当前会员的提货券个数===============================================================================
	 */
	public String  getTiHuoJuanNumber(PageData pd)throws Exception{
		return (String)dao.findForObject("Wxmember_tihuojuanMapper.getTiHuoJuanNumber", pd);
	}
	public String  getTiHuoJuanMoneyById(PageData pd)throws Exception{
		return (String)dao.findForObject("Wxmember_tihuojuanMapper.getTiHuoJuanMoneyById", pd);
	}
	public void editTiHuoJuan(PageData pd)throws Exception{
		dao.update("Wxmember_tihuojuanMapper.editTiHuoJuan", pd);
	}
	
	
	
	
	/*
	* 获取会员购物车的总价==================================================================================
	*/
	public String sumShopcartById(PageData pd)throws Exception{
		return (String)dao.findForObject("WxShopcartMapper.sumShopcartById", pd);
	}
	public String countShopcartNumber(PageData pd)throws Exception{
		return (String)dao.findForObject("WxShopcartMapper.countShopcartNumber", pd);
	}
	public PageData findShopCartById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WxShopcartMapper.findShopCartById", pd);
	}
	public void deleteShopCartById(PageData pd)throws Exception{
		dao.delete("WxShopcartMapper.deleteShopCartById", pd);
	}
	public void updateShopCartById(PageData pd)throws Exception{
		dao.update("WxShopcartMapper.updateShopCartById", pd);
	}
	public void saveShopCartById(PageData pd)throws Exception{
		dao.save("WxShopcartMapper.saveShopCartById", pd);
 	}
	public List<PageData> findShopCartList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WxShopcartMapper.findShopCartList", pd);
	}
	 
	
	
	
	
	
	
	
	
	/*
	 * 获取地址详情============================================================================================
	 */
	public PageData findAddressDetail(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WxAddressMapper.findAddressDetail", pd);
	}
	
}

