package dingzhen.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dingzhen.dao.WokeDao;
import dingzhen.service.WokeService;

@Service("wokeService")
public class WokeServiceImpl<T> implements WokeService<T>{
	
	@Autowired
	private WokeDao<T> dao;
	
	@Override
	public List findWoke(T t) throws Exception {
		return dao.findWoke(t);
	}
	@Override
	public int countWoke(T t) throws Exception {
		return dao.countWoke(t);
	}
	@Override
	public int existlevel(T t) throws Exception {
		return dao.existlevel(t);
	}
	public List findallhomework(T t) throws Exception {
		return dao.findallhomework(t);
	}
	@Override
	public int countallhomework(T t) throws Exception {
		return dao.countallhomework(t);
	}
	@Override
	public T findOneUser(Integer id) throws Exception {
		return dao.findOneUser(id);
	}
	@Override
	public void addWoke(T t) throws Exception {
		dao.addWoke(t);
	}
	@Override
	public void updateWoke(T t) throws Exception {
		dao.updateWoke(t);
	}
	@Override
	public void updateWokelevel(T t) throws Exception {
		dao.updateWokelevel(t);
	}
	@Override
	public void insertWokelevel(T t) throws Exception {
		dao.insertWokelevel(t);
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
