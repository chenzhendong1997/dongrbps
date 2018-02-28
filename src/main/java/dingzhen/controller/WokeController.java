package dingzhen.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;

import dingzhen.entity.Project;
import dingzhen.entity.Woke;
import dingzhen.service.ProjectService;
import dingzhen.service.WokeService;
import dingzhen.util.StringUtil;
import dingzhen.util.WriterUtil;


@Controller
@RequestMapping("woke")
public class WokeController {

	private int page;
	private int rows;
	private Woke woke;
	private Project project;
	@Autowired
	private WokeService<Woke> wokeService;
	@Autowired
	private ProjectService<Project> projectService;
	
	
	@RequestMapping("woke")
	public String index(HttpServletRequest request,HttpServletResponse response){
		if((Integer)request.getSession().getAttribute("roleId")==1) {
			return "jiaowuwoke";
		}else if((Integer)request.getSession().getAttribute("roleId")==5) {
			return "jiaowuwoke";
		}else {
			return "woke";
		}
	}
	
	@RequestMapping("allhomework")
	public String all(HttpServletRequest request,HttpServletResponse response){
		if((Integer)request.getSession().getAttribute("roleId")==5) {
			return "studentwoke";
		}else {
			return "allhomework";
		}
	}
	
	
	@RequestMapping("wokeList")
	public void wokeList(HttpServletRequest request,HttpServletResponse response){
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			woke = new Woke();
			woke.setPage((page-1)*rows);
			woke.setRows(rows);
			woke.setUserName(request.getParameter("userName"));
			JSONObject jsonObj = new JSONObject();
			if((Integer)request.getSession().getAttribute("roleId")==5) {
				woke.setStudentId((Integer)request.getSession().getAttribute("userId"));
				List<Woke> list= wokeService.findWoke(woke);
				int total = wokeService.countWoke(woke);
				jsonObj.put("total",total );
				jsonObj.put("rows", list);
			}else {
				List<Woke> list= wokeService.findWoke(woke);
				int total = wokeService.countWoke(woke);
				jsonObj.put("total",total );
				jsonObj.put("rows", list);
			}
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//展示所有作业，老师上传修改作业
	@RequestMapping("allhomeworkList")
	public void allList(HttpServletRequest request,HttpServletResponse response){
		Integer teacherId=(Integer) request.getSession().getAttribute("userId");
		String selflevel=request.getParameter("selflevel");
		String selfscore=request.getParameter("selfscore");
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			woke = new Woke();
			woke.setPage((page-1)*rows);
			woke.setRows(rows);
			JSONObject jsonObj = new JSONObject();
			if((Integer)request.getSession().getAttribute("roleId")==4) {
				woke.setTeacherId(teacherId);
				List<Woke> list= wokeService.findallhomework(woke);
				int total = wokeService.countallhomework(woke);
				jsonObj.put("total",total );
				jsonObj.put("rows", list);
			}else {
				List<Woke> list= wokeService.findallhomework(woke);
				int total = wokeService.countallhomework(woke);
				jsonObj.put("total",total );
				jsonObj.put("rows", list);
			}
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveallwork")
	public void reserveUser(HttpServletRequest request,Woke woke,HttpServletResponse response){
		String id = request.getParameter("id");
		String question=request.getParameter("question");
		String questionstandard=request.getParameter("questionstandard");
		String type =request.getParameter("type");
		String selfscore =request.getParameter("selfscore");
		String selflevel =request.getParameter("selflevel");
		Integer teacherId=(Integer) request.getSession().getAttribute("userId");
		System.out.println(teacherId);
		JSONObject result=new JSONObject();
		try {
			if (StringUtil.isNotEmpty(id)) {   // Id不为空 说明是修改
				woke.setId(Integer.parseInt(id));
				woke.setQuestion(question);
				woke.setQuestionstandard(questionstandard);
				woke.setType(type);
				woke.setSelfscore(selfscore);
				woke.setSelflevel(selflevel);
				wokeService.updateWoke(woke);
				result.put("success", true);
			}else {   // 添加
				woke.setQuestion(question);
				woke.setQuestionstandard(questionstandard);
				woke.setType(type);
				woke.setTeacherId(teacherId);
				wokeService.addWoke(woke);
				result.put("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	//老师评分评级
	@RequestMapping("reservewoke")
	public void reservewoke(HttpServletRequest request,Woke woke,HttpServletResponse response){
		String id = request.getParameter("id");
		String teacherscore=request.getParameter("teacherscore");
		String teacherlevel=request.getParameter("teacherlevel");
		JSONObject result=new JSONObject();
		try {
				woke.setId(Integer.parseInt(id));
				woke.setTeacherscore(teacherscore);
				woke.setTeacherlevel(teacherlevel);
				wokeService.updateWokelevel(woke);
				result.put("success", true);
		} catch (Exception e){
			e.printStackTrace();
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	//学生自评
		@RequestMapping("reservewokelevel")
		public void reservewokelevel(HttpServletRequest request,HttpServletResponse response){
			String selfscore=request.getParameter("selfscore");
			String selflevel=request.getParameter("selflevel");
			String type=request.getParameter("type");
			String projectname=request.getParameter("projectname");
			Integer assignId=Integer.parseInt(request.getParameter("assignId"));
			Integer studentId=(Integer) request.getSession().getAttribute("userId");
			JSONObject result=new JSONObject();
			try {
				if(type.equals("练习")){
					woke.setStudentId(studentId);
					woke.setAssignId(assignId);
					int count=wokeService.existlevel(woke);
					if(count!=0) {//修改
						woke.setSelflevel(selflevel);
						woke.setSelfscore(selfscore);
						wokeService.updateWokelevel(woke);
						result.put("success", true);
					}else {//新增
						woke.setSelflevel(selflevel);
						woke.setSelfscore(selfscore);
						wokeService.insertWokelevel(woke);
						result.put("success", true);
					}
				}else {
					Project project=new Project();
					System.out.println(studentId);
					project.setStudentId(studentId);
					project.setAssignId(assignId);
					int count=projectService.existlevel(project);
					if(count!=0) {//修改
						project.setSelflevel(selflevel);
						project.setSelfscore(selfscore);
						projectService.updateProjectlevel(project);
						result.put("success", true);
					}else {//新增
						project.setSelflevel(selflevel);
						project.setSelfscore(selfscore);
						projectService.insertProjectlevel(project);
						result.put("success", true);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.put("success", true);
				result.put("errorMsg", "对不起，操作失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		@RequestMapping("insert")
		public void insert(HttpServletRequest request,@RequestParam("file") CommonsMultipartFile file){
			JSONObject result=new JSONObject();
			try {
				System.out.println("111");
				String path="E:/eclipse/dongrbps/src/main/webapp/studentwork/"+new Date().getTime()+file.getOriginalFilename();
				File newFile=new File(path);
				file.transferTo(newFile);
				result.put("success", true);
			}catch (Exception e) {
				e.printStackTrace();
				result.put("success", true);
				result.put("errorMsg", "对不起，操作失败");
			}
		}
		
//	@RequestMapping("deleteUser")
//	public void delUser(HttpServletRequest request,HttpServletResponse response){
//		JSONObject result=new JSONObject();
//		try {
//			String[] ids=request.getParameter("ids").split(",");
//			for (String id : ids) {
//				userService.deleteUser(Integer.parseInt(id));
//			}
//			result.put("success", true);
//			result.put("delNums", ids.length);
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.put("errorMsg", "对不起，删除失败");
//		}
//		WriterUtil.write(response, result.toString());
//	}
	
}
