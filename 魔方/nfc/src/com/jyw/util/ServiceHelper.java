package com.jyw.util;

import com.jyw.service.business.AddressService;
import com.jyw.service.business.CategoryService;
import com.jyw.service.business.LunchService;
import com.jyw.service.system.menu.MenuService;
import com.jyw.service.system.role.RoleService;
import com.jyw.service.system.user.UserService;



/**
 * @author cyr
 * 获取Spring容器中的service bean
 */
public final class ServiceHelper {
	
	public static Object getService(String serviceName){
		return Const.WEB_APP_CONTEXT.getBean(serviceName);
	}
	
	public static UserService getUserService(){
		return (UserService) getService("userService");
	}
	
	public static RoleService getRoleService(){
		return (RoleService) getService("roleService");
	}
	
	public static MenuService getMenuService(){
		return (MenuService) getService("menuService");
	}
	//属性类别服务层
	public static CategoryService getCategoryService(){
		return (CategoryService) getService("categoryService");
	}
	//属性商品服务层
	public static LunchService getLunchService(){
		return (LunchService) getService("lunchService");
	}
	//属性地址服务层
	public static AddressService getAddressService(){
		return (AddressService) getService("addressService");
	}
	
	
}
