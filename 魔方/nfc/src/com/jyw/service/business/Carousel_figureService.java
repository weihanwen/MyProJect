package com.jyw.service.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jyw.dao.DaoSupport;
import com.jyw.entity.Page;
import com.jyw.util.PageData;


@Service("carousel_figureService")
public class Carousel_figureService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("Carousel_figureMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Carousel_figureMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("Carousel_figureMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Carousel_figureMapper.datalistPage", page);
	}
	
	
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Carousel_figureMapper.findById", pd);
	}
	
	/*
	*获取全部未过期的轮播图
	*/
	public List<PageData> listAllOk(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Carousel_figureMapper.listAllOk", pd);
	}
	
	 
	
}

