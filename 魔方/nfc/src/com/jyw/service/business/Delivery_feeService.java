package com.jyw.service.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jyw.dao.DaoSupport;
import com.jyw.entity.Page;
import com.jyw.util.PageData;


@Service("delivery_feeService")
public class Delivery_feeService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("Delivery_feeMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Delivery_feeMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("Delivery_feeMapper.edit", pd);
	}
	
 
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Delivery_feeMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Delivery_feeMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Delivery_feeMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Delivery_feeMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	* 通过份数获取配送金额
	*/
	public String getMoneyByNumber(String number)throws Exception{
		return (String)dao.findForObject("Delivery_feeMapper.getMoneyByNumber", number);
	}
	
}

