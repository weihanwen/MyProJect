package com.jyw.service.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jyw.dao.DaoSupport;
import com.jyw.entity.Page;
import com.jyw.util.PageData;


@Service("orderService")
public class   OrderService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	 
	
	/*
	*订单列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrderMapper.datalistPage", page);
	}
	

	/*
	 *全部订单已order-status划分
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OrderMapper.listAll", pd);
	}
	
	/*
	 *订单下的商品列表-order_id
	 */
	public List<PageData> listLunchByOrder(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OrderMapper.listLunchByOrder", pd);
	}
	 
	
	/*
	*订单详情
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OrderMapper.findById", pd);
	}
	/*
	 *根据菜单名搜索的时候，获取所有有关的订单ID
	 */
	public String getOrderStrByLunchName(PageData pd)throws Exception{
		return (String)dao.findForObject("OrderMapper.getOrderStrByLunchName", pd);
	}
	 
	/*
	 *销售明细
	 */
	public List<PageData> saleHistory(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrderMapper.saleHistorydatalistPage", page);
	}
	
	/*
	 *总的财富订单统计
	 */
	public PageData totalOrder(Page page)throws Exception{
		return (PageData)dao.findForObject("OrderMapper.totalOrder", page);
	}
	
	 
	
}

