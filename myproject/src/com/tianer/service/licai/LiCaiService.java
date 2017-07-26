package com.tianer.service.licai;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("lLiCaiService")
public class LiCaiService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/*
	* 新增用户
	*/
	public void saveUser(PageData pd)throws Exception{
		dao.save("LiCaiMapper.saveUser", pd);
	}
	
	/*
	* 查找用户
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData) dao.findForObject("LiCaiMapper.findById", pd);
	}
	
	/*
	* 新增预约的理财信息
	*/
	public void saveLiCaiInfor(PageData pd)throws Exception{
		dao.save("LiCaiMapper.saveLiCaiInfor", pd);
	}
	
	   
}

