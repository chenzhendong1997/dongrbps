package dingzhen.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dingzhen.dao.ClassDao;
import dingzhen.service.ClassService;

@Service("classService")
public class ClassServiceImpl<T> implements ClassService<T>{
	
	@Autowired
	private ClassDao<T> dao;
	
	@Override
	public List findClass(T t) throws Exception {
		return dao.findClass(t);
	}
	@Override
	public int countClass(T t) throws Exception {
		return dao.countClass(t);
	}
	@Override
	public T findOneUser(Integer id) throws Exception {
		return dao.findOneUser(id);
	}
	@Override
	public void addClass(T t) throws Exception {
		dao.addClass(t);
	}
	@Override
	public void updateClass(T t) throws Exception {
		dao.updateClass(t);
	}
	@Override
	public void deleteClass(Integer id) throws Exception {
		dao.deleteClass(id);
	}
	@Override
	public T loginUser(Map<String,String> map) throws Exception {
		return dao.loginUser(map);
	}
	@Override
	public T existClassWithClassName(String userName) throws Exception {
		return dao.existClassWithClassName(userName);
	}
	@Override
	public T existUserWithRoleId(Integer roleId) throws Exception {
		return dao.existUserWithRoleId(roleId);
	}

	
}
