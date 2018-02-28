package dingzhen.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dingzhen.dao.ProjectDao;
import dingzhen.service.ProjectService;

@Service("projectService")
public class ProjectServiceImpl<T> implements ProjectService<T>{
	
	@Autowired
	private ProjectDao<T> dao;
	
	@Override
	public List findProject(T t) throws Exception {
		return dao.findProject(t);
	}
	@Override
	public int existlevel(T t) throws Exception {
		return dao.existlevel(t);
	}
	@Override
	public void updateProjectlevel(T t) throws Exception {
		dao.updateProjectlevel(t);
	}
	@Override
	public void insertProjectlevel(T t) throws Exception {
		dao.insertProjectlevel(t);
	}
	@Override
	public int countProject(T t) throws Exception {
		return dao.countProject(t);
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
	public void updateProject(T t) throws Exception {
		dao.updateProject(t);
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
