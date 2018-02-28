package dingzhen.dao;

import java.util.List;
import java.util.Map;

public interface QuestionDao<T> {

	// 查询所有
	public  List<T> findQuestion(T t) throws Exception;
	public  List<T> findQuestionspace(T t) throws Exception;
	
	public  List<T> showquestion(T t) throws Exception;
	
	public  List<T> findQuestionanswer(T t) throws Exception;
	
	// 数量
	public  int countQuestion(T t) throws Exception;
	public  Integer selectid(T t) throws Exception;
	
	// 通过ID查询
	public  T findOneUser(Integer id) throws Exception;
	
	// 新增
	public  void addQuestion(T t) throws Exception;
	
	public  void insertcreatequestion(T t) throws Exception;
	
	public  void addQuestionspace(T t) throws Exception;
	
	public  void createquestion(T t) throws Exception;
	
	public  void createtest(T t) throws Exception;
	
	// 修改
	public  void updateUser(T t) throws Exception;
	
	public  void updateQuestionspace(T t) throws Exception;
	
	// 删除
	public  void deleteUser(Integer id) throws Exception;
	
	public  void deleteQuestionspace(Integer id) throws Exception;
	
	// 登录
	public  T loginUser(Map<String, String> map) throws Exception;
	
	//通过用户名判断是否存在，（新增时不能重名）
	public  int existUserWithUserName(T t) throws Exception;
	
	// 通过角色判断是否存在
	public  T existUserWithRoleId(Integer roleId) throws Exception;
	
}
