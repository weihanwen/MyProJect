package com.tianer.service.business.w;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("wService")
public class WService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 新增log日志
	 */
	public void saveLog(String order_id,String message,String type)throws Exception{
		PageData logpd=new PageData();
		logpd.put("order_id", order_id);
		logpd.put("message", message);
		logpd.put("type", type);
 		dao.save("WMapper.saveLog", logpd);
	}
	
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("WMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("WMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("WMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("WMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

