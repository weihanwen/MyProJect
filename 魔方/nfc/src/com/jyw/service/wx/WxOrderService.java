package com.jyw.service.wx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jyw.dao.DaoSupport;
import com.jyw.entity.Page;
import com.jyw.util.PageData;


@Service("wxOrderService")
public class   WxOrderService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	 
	
	/*
	*订单列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WxOrderMapper.datalistPage", page);
	}
 
	
	/*
	*订单详情
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WxOrderMapper.findById", pd);
	}
	
	/*
	* 修改订单状态
	*/
	public void changeStatus(PageData pd)throws Exception{
		dao.update("WxOrderMapper.changeStatus", pd);
	}
	 
	 
	
	 
	
}

