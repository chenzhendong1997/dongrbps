package dingzhen.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dingzhen.dao.LeaveDao;
import dingzhen.service.LeaveService;

@Service("leaveService")
public class LeaveServiceImpl<T> implements LeaveService<T>{
	
	@Autowired
	private LeaveDao<T> dao;
	
	@Override
	public List findLeave(T t) throws Exception {
		return dao.findLeave(t);
	}
	@Override
	public int countLeave(T t) throws Exception {
		return dao.countLeave(t);
	}
	@Override
	public T findOneUser(Integer id) throws Exception {
		return dao.findOneUser(id);
	}
	@Override
	public void addLeave(T t) throws Exception {
		dao.addLeave(t);
	}
	@Override
	public void updateLeave(T t) throws Exception {
		dao.updateLeave(t);
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
