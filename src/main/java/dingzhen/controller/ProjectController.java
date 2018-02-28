package dingzhen.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONObject;
import dingzhen.entity.Project;
import dingzhen.entity.Woke;
import dingzhen.service.ProjectService;
import dingzhen.util.StringUtil;
import dingzhen.util.WriterUtil;


@Controller
@RequestMapping("project")
public class ProjectController {

	private int page;
	private int rows;
	private Project project;
	@Autowired
	private ProjectService<Project> projectService;
	
	
	@RequestMapping("project")
	public String index(HttpServletRequest request,HttpServletResponse response){
		if((Integer)request.getSession().getAttribute("roleId")==1) {
			return "jiaowuproject";
		}else if((Integer)request.getSession().getAttribute("roleId")==5) {
			return "jiaowuproject";
		}else {
			return "project";
		}
	}
	
	
	@RequestMapping("projectList")
	public void projectList(HttpServletRequest request,HttpServletResponse response){
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			project = new Project();
			project.setPage((page-1)*rows);
			project.setRows(rows);
			JSONObject jsonObj = new JSONObject();
			if((Integer)request.getSession().getAttribute("roleId")==5) {
				project.setStudentId((Integer)request.getSession().getAttribute("userId"));
				List<Project> list= projectService.findProject(project);
				int total = projectService.countProject(project);
				jsonObj.put("total",total );
				jsonObj.put("rows", list);
			}else {
				List<Project> list= projectService.findProject(project);
				int total = projectService.countProject(project);
				jsonObj.put("total",total );
				jsonObj.put("rows", list);
			}
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 修改
	@RequestMapping("reserveproject")
	public void reserveproject(HttpServletRequest request,Project project,HttpServletResponse response){
		String id = request.getParameter("id");
		String teacherscore=request.getParameter("teacherscore");
		String teacherlevel=request.getParameter("teacherlevel");
		JSONObject result=new JSONObject();
		try {
			project.setId(Integer.parseInt(id));
			project.setTeacherscore(teacherscore);
			project.setTeacherlevel(teacherlevel);
			projectService.updateProject(project);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
//	
//	
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
