package dingzhen.dao;

import java.util.List;
import java.util.Map;

public interface ProjectDao<T> {

	// 查询所有
	public  List<T> findProject(T t) throws Exception;
	
	// 数量
	public  int countProject(T t) throws Exception;
	public  int existlevel(T t) throws Exception;
	
	// 通过ID查询
	public  T findOneUser(Integer id) throws Exception;
	
	// 新增
	public  void addUser(T t) throws Exception;
	
	// 修改
	public  void updateProject(T t) throws Exception;
	public  void updateProjectlevel(T t) throws Exception;
	public  void insertProjectlevel(T t) throws Exception;
	
	// 删除
	public  void deleteUser(Integer id) throws Exception;
	
	// 登录
	public  T loginUser(Map<String, String> map) throws Exception;
	
	//通过用户名判断是否存在，（新增时不能重名）
	public  T existUserWithUserName(String userName) throws Exception;
	
	// 通过角色判断是否存在
	public  T existUserWithRoleId(Integer roleId) throws Exception;
	
}
