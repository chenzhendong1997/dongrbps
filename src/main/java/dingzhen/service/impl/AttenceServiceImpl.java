package dingzhen.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dingzhen.dao.AttenceDao;
import dingzhen.service.AttenceService;

@Service("attenceService")
public class AttenceServiceImpl<T> implements AttenceService<T>{
	
	@Autowired
	private AttenceDao<T> dao;
	
	@Override
	public List findAttence(T t) throws Exception {
		return dao.findAttence(t);
	}
	@Override
	public int countAttence(T t) throws Exception {
		return dao.countAttence(t);
	}
	@Override
	public List findAlluser(T t) throws Exception {
		return dao.findAlluser(t);
	}
	@Override
	public void addAttence(T t) throws Exception {
		dao.addAttence(t);
	}
	@Override
	public void updateAttence(T t) throws Exception {
		dao.updateAttence(t);
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
