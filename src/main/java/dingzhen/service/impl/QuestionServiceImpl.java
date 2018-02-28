package dingzhen.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dingzhen.dao.QuestionDao;
import dingzhen.service.QuestionService;

@Service("questionService")
public class QuestionServiceImpl<T> implements QuestionService<T>{
	
	@Autowired
	private QuestionDao<T> dao;
	
	@Override
	public List findQuestion(T t) throws Exception {
		return dao.findQuestion(t);
	}
	
	@Override
	public List findQuestionanswer(T t) throws Exception {
		return dao.findQuestionanswer(t);
	}
	@Override
	public List findQuestionspace(T t) throws Exception {
		return dao.findQuestionspace(t);
	}
	@Override
	public List showquestion(T t) throws Exception {
		return dao.showquestion(t);
	}
	@Override
	public int countQuestion(T t) throws Exception {
		return dao.countQuestion(t);
	}
	@Override
	public Integer selectid(T t) throws Exception {
		return dao.selectid(t);
	}
	@Override
	public T findOneUser(Integer id) throws Exception {
		return dao.findOneUser(id);
	}
	@Override
	public void addQuestion(T t) throws Exception {
		dao.addQuestion(t);
	}
	@Override
	public void insertcreatequestion(T t) throws Exception {
		dao.insertcreatequestion(t);
	}
	@Override
	public void createquestion(T t) throws Exception {
		dao.createquestion(t);
	}
	@Override
	public void createtest(T t) throws Exception {
		dao.createtest(t);
	}
	@Override
	public void addQuestionspace(T t) throws Exception {
		dao.addQuestionspace(t);
	}
	@Override
	public void updateUser(T t) throws Exception {
		dao.updateUser(t);
	}
	@Override
	public void updateQuestionspace(T t) throws Exception {
		dao.updateQuestionspace(t);
	}
	@Override
	public void deleteUser(Integer id) throws Exception {
		dao.deleteUser(id);
	}
	@Override
	public void deleteQuestionspace(Integer id) throws Exception {
		dao.deleteQuestionspace(id);
	}
	@Override
	public T loginUser(Map<String,String> map) throws Exception {
		return dao.loginUser(map);
	}
	@Override
	public int existUserWithUserName(T t) throws Exception {
		return dao.existUserWithUserName(t);
	}
	@Override
	public T existUserWithRoleId(Integer roleId) throws Exception {
		return dao.existUserWithRoleId(roleId);
	}

	
}
