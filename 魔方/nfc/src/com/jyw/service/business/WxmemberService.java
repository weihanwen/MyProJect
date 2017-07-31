package com.jyw.service.business;

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
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WxmemberMapper.datalistPage", page);
	}
	 
	 
	 
	
	 
	
}

