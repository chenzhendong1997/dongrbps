package dingzhen.service;

import java.util.List;
import java.util.Map;

public interface ClassService<T> {
	
	// 查询所有
	public  List<T> findClass(T t) throws Exception;
	
	// 数量
	public  int countClass(T t) throws Exception;
	
	// 通过ID查询
	public  T findOneUser(Integer id) throws Exception;
	
	// 新增
	public  void addClass(T t) throws Exception;
	
	// 修改
	public  void updateClass(T t) throws Exception;
	
	// 删除
	public  void deleteClass(Integer id) throws Exception;
	
	// 登录
	public  T loginUser(Map<String, String> map) throws Exception;
	
	//通过用户名判断是否存在，（新增时不能重名）
	public  T existClassWithClassName(String userName) throws Exception;
	
	// 通过角色判断是否存在
	public  T existUserWithRoleId(Integer roleId) throws Exception;
	
}
