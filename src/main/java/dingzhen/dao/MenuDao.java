package dingzhen.dao;

import java.util.List;
import java.util.Map;


public interface MenuDao<T> {
	
	public  List<T> findMenu(T t) throws Exception;
	
	public  int countMenu(T t) throws Exception;
	
	public  void addMenu(T t) throws Exception;
	
	public  void updateMenu(T t) throws Exception;
	
	public  void deleteMenu(Integer menuId) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public  List<T> menuTree(Map map) throws Exception;
	
}
