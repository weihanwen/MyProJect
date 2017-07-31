package com.jyw.service.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jyw.dao.DaoSupport;
import com.jyw.entity.Page;
import com.jyw.util.PageData;


@Service("redpackageService")
public class   RedpackageService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("RedpackageMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("RedpackageMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("RedpackageMapper.edit", pd);
	}
	 
	
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RedpackageMapper.datalistPage", page);
	}
	 
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RedpackageMapper.findById", pd);
	}
	
 
	
	/*
	 * 红包的条件详情 
	 */
	public PageData findByIdCondition(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RedpackageMapper.findByIdCondition", pd);
	}
 	/*
	*红包的条件 列表
	*/
	public List<PageData> listReceiveCondition(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RedpackageMapper.listReceiveCondition", pd);
	}
	 
	
	 
	
}

