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
	* 新增会员
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
	* 通过id获取数据-通过openid/wxmember_id
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WxmemberMapper.findById", pd);
	}
	
  
	
	 
	
}

