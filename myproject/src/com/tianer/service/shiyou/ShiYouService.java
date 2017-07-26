package com.tianer.service.shiyou;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("shiYouService")
public class ShiYouService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增用户
	*/
	public void saveUser(PageData pd)throws Exception{
		dao.save("ShiYouMapper.saveUser", pd);
	}
	
	/*
	 * 修改用户
	 */
	public void editUser(PageData pd)throws Exception{
		dao.update("ShiYouMapper.editUser", pd);
	}
	/*
	* 查看用户详情
	*/
	public PageData findUserById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ShiYouMapper.findUserById", pd);
	}
	
	
	/*
	* 新增订单
	*/
	public void saveOrder(PageData pd)throws Exception{
		dao.save("ShiYouMapper.saveOrder", pd);
	}
	
	/*
	 * 修改用户
	 */
	public void editOrder(PageData pd)throws Exception{
		dao.update("ShiYouMapper.editOrder", pd);
	}
	/*
	* 获取订单信息
	*/
	public PageData findOrderById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ShiYouMapper.findOrderById", pd);
	}
	
	

	/*
	 * 新增卡号
	 */
	public void saveCard(PageData pd)throws Exception{
		dao.save("ShiYouMapper.saveCard", pd);
	}
	
	
	/*
	* 获取卡号信息
	*/
	public PageData findCardById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ShiYouMapper.findCardById", pd);
	}
	
	
	
	/*
	* 新增要充值油卡的人员信息
	*/
	public void saveInforemation(PageData pd)throws Exception{
		dao.save("ShiYouMapper.saveInforemation", pd);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("ShiYouMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("ShiYouMapper.edit", pd);
	}
 	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ShiYouMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ShiYouMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ShiYouMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ShiYouMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

