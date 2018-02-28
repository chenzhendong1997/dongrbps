package dingzhen.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import dingzhen.entity.Question;
import dingzhen.entity.User;
import dingzhen.service.QuestionService;
import dingzhen.util.StringUtil;
import dingzhen.util.WriterUtil;
import dingzhen.util.HtmlUtil;


@Controller
@RequestMapping("question")
public class QuestionController {

	private int page;
	private int rows;
	private Question question;
	@Autowired
	private QuestionService<Question> questionService;
	
	
	@RequestMapping("question")
	public String index(HttpServletRequest request,HttpServletResponse response){
		if((Integer)request.getSession().getAttribute("roleId")==5){
			return "answerquestion";
		}else {
			return "question";
		}
	}
	
	@RequestMapping("questionspace")
	public String questionspace(HttpServletRequest request,HttpServletResponse response){
		return "questionspace";
	}
	
	@RequestMapping("questionanswer")
	public String questionanswer(HttpServletRequest request,HttpServletResponse response){
		return "showstudentanswer";
	}
	
	
	@RequestMapping("questionList")
	public void questionList(HttpServletRequest request,HttpServletResponse response){
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			question = new Question();
			question.setPage((page-1)*rows);
			question.setRows(rows);
			List<Question> list= questionService.findQuestion(question);
			int total = questionService.countQuestion(question);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",total );
			jsonObj.put("rows", list);
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("questionspaceList")
	public void questionspaceList(HttpServletRequest request,HttpServletResponse response){
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			question = new Question();
			question.setPage((page-1)*rows);
			question.setRows(rows);
			List<Question> list= questionService.findQuestionspace(question);
			int total = questionService.countQuestion(question);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",total );
			jsonObj.put("rows", list);
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("studentanswerList")
	public void studentanswerList(HttpServletRequest request,HttpServletResponse response){
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			question = new Question();
			question.setPage((page-1)*rows);
			question.setRows(rows);
			List<Question> list= questionService.findQuestionanswer(question);
			int total = questionService.countQuestion(question);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",total );
			jsonObj.put("rows", list);
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 显示问卷
	@RequestMapping("showquestion")
	public void reserveUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		question.setCreateId(Integer.parseInt(request.getParameter("id")));
		List<Question> list = questionService.showquestion(question);
        HtmlUtil.writerJson(response, list);
	}
	
	// 保存答案
	@RequestMapping("submitquestion")
	public void reserve(HttpServletRequest request,HttpServletResponse response){
		String [] questionanswer = request.getParameter("questionanswer").split(",");
		System.out.println(questionanswer);
		Integer testId = Integer.parseInt(request.getParameter("testId"));
		Integer studentId=(Integer) request.getSession().getAttribute("userId");
		JSONObject result=new JSONObject();
		try {
			question = new Question();
			question.setUserId(studentId);
			question.setTestId(testId);
			if(questionService.existUserWithUserName(question)!=0) {//如果已经答过题
				result.put("errorMsg", "已经答过这套题了");
			}else {
				for (int i=0;i<questionanswer.length;i++) {
					question.setUserId(studentId);
					question.setTestId(testId);
					question.setQuestionanswer(questionanswer[i]);
					questionService.addQuestion(question);
				}
				result.put("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	// 新增或修改
		@RequestMapping("reserveQuestionspace")
		public void reserveQuestionspace(HttpServletRequest request,HttpServletResponse response){
			String id = request.getParameter("id");
			String one = request.getParameter("one");
			String two = request.getParameter("two");
			String three = request.getParameter("three");
			String four = request.getParameter("four");
			String questionspace = request.getParameter("question");
			JSONObject result=new JSONObject();
			try {
				question.setOne(one);
				question.setTwo(two);
				question.setThree(three);
				question.setFour(four);
				question.setQuestion(questionspace);
				if (StringUtil.isNotEmpty(id)) {   // userId不为空 说明是修改
					question.setId(Integer.parseInt(id));
					questionService.updateQuestionspace(question);
					result.put("success", true);
				}else {   // 添加
					questionService.addQuestionspace(question);
					result.put("success", true);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.put("success", true);
				result.put("errorMsg", "对不起，操作失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		// 新增或修改
				@SuppressWarnings("unused")
				@RequestMapping("reserveinsertquestion")
				public void reserveinsertquestion(HttpServletRequest request,HttpServletResponse response){
					String[] ids=request.getParameter("id").split(",");
					JSONObject result=new JSONObject();
					try {
						Integer adminId= (Integer)request.getSession().getAttribute("userId");
						String questionname=request.getParameter("questionname");
						question.setAdminId(adminId);
						question.setQuestionname(questionname);
						Integer id=questionService.selectid(question);
						if(id==null) {
							questionService.createquestion(question);
							question.setAdminId(adminId);
							question.setQuestionname(questionname);
							Integer id1=questionService.selectid(question);
							for (int i=0;i<ids.length;i++) {
								question.setCreateId(id1);
								question.setSpacecode(Integer.parseInt(ids[i]));
								questionService.insertcreatequestion(question);
							}
							result.put("success", true);
						}else {
							result.put("errorMsg", "该问卷已存在，请更换问卷名");
						}
					} catch (Exception e) {
						e.printStackTrace();
						result.put("success", true);
						result.put("errorMsg", "对不起，操作失败");
					}
					WriterUtil.write(response, result.toString());
				}
	
//	
	@RequestMapping("deleteUser")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				System.out.println(id);
				questionService.deleteQuestionspace(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
}
