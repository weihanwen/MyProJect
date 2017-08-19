package com.jyw.service.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jyw.dao.DaoSupport;
import com.jyw.entity.Page;
import com.jyw.util.PageData;


@Service("lunchService")
public class LunchService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("LunchMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("LunchMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("LunchMapper.edit", pd);
	}
	 
	
	/*
	 * 修改排序
	 */
	public void editSort(PageData pd)throws Exception{
		dao.update("LunchMapper.editSort", pd);
	}
	
	
	/*
	 * 修改库存
	 */
	public void editNumber(PageData pd)throws Exception{
		dao.update("LunchMapper.editNumber", pd);
	}
	
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("LunchMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)上架商品
	*/
	public List<PageData> listAllUpShelves(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("LunchMapper.listAllUpShelves", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LunchMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("LunchMapper.deleteAll", ArrayDATA_IDS);
	}
	
	
	/*
	* 获取最高得id
	*/
	public Integer getMaxIdByLunch()throws Exception{
		return (Integer)dao.findForObject("LunchMapper.getMaxIdByLunch", null);
	}
	
	
	
	//微信端开始接口===========================================================================================
	/*
	 * 通过ID获取库存以及所对应嘚版本号
	 */
	public PageData findByIdForKunCun(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LunchMapper.findByIdForKunCun", pd);
	}
 	/*
	* 通过id获取数据
	*/
	public PageData findByIdForWx(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LunchMapper.findByIdForWx", pd);
	}
	/*
	 *通过分类ID获取上架商品
	 */
	public List<PageData> listAllByCate(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("LunchMapper.listAllByCate", pd);
	}
	
	
	
	
}

