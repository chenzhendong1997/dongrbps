package dingzhen.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dingzhen.dao.UserDao;
import dingzhen.service.UserService;

@Service("userService")
public class UserServiceImpl<T> implements UserService<T>{
	
	@Autowired
	private UserDao<T> dao;
	
	@Override
	public List findUser(T t) throws Exception {
		return dao.findUser(t);
	}
	@Override
	public int countUser(T t) throws Exception {
		return dao.countUser(t);
	}
	@Override
	public T findOneUser(Integer id) throws Exception {
		return dao.findOneUser(id);
	}
	@Override
	public void addUser(T t) throws Exception {
		dao.addUser(t);
	}
	@Override
	public void updateUser(T t) throws Exception {
		dao.updateUser(t);
	}
	@Override
	public void deleteUser(Integer id) throws Exception {
		dao.deleteUser(id);
	}
	@Override
	public T loginUser(Map<String,String> map) throws Exception {
		return dao.loginUser(map);
	}
	@Override
	public T existUserWithUserName(String userName) throws Exception {
		return dao.existUserWithUserName(userName);
	}
	@Override
	public T existUserWithRoleId(Integer roleId) throws Exception {
		return dao.existUserWithRoleId(roleId);
	}

	
}
