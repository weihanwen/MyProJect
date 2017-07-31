package com.jyw.service.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jyw.dao.DaoSupport;
import com.jyw.entity.Page;
import com.jyw.util.PageData;


@Service("weekmeal_cardService")
public class   Weekmeal_cardService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("Weekmeal_cardMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Weekmeal_cardMapper.delete", pd);
	}
	
	/*
	* 修改周卡状态
	*/
	public void changeStatus(PageData pd)throws Exception{
		dao.update("Weekmeal_cardMapper.changeStatus", pd);
	}
	 
	
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Weekmeal_cardMapper.datalistPage", page);
	}
	 
	
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Weekmeal_cardMapper.findById", pd);
	}
	
  
	

	/*
	 *已购买的月票的列表
	 */
	public List<PageData> listByBuyWeekmeldatalistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Weekmeal_cardMapper.listByBuyWeekmeldatalistPage", page);
	}
	
	
	 
	
}

