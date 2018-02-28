package dingzhen.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONObject;
import dingzhen.entity.Class;
import dingzhen.service.ClassService;
import dingzhen.util.StringUtil;
import dingzhen.util.WriterUtil;


@Controller
@RequestMapping("classroom")
public class ClassController {

	private int page;
	private int rows;
	private Class classroom;
	@Autowired
	private ClassService<Class> classService;
	
	
	@RequestMapping("classIndex")
	public String index(){
		return "class";
	}
	
	
	@RequestMapping("classList")
	public void classList(HttpServletRequest request,HttpServletResponse response){
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			classroom = new Class();
			classroom.setPage((page-1)*rows);
			classroom.setRows(rows);
			classroom.setClass_name(request.getParameter("class_name"));
			List<Class> list= classService.findClass(classroom);
			int total = classService.countClass(classroom);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",total );
			jsonObj.put("rows", list);
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveClass")
	public void reserveUser(HttpServletRequest request,Class classroom,HttpServletResponse response){
		String classid = request.getParameter("id");
		JSONObject result=new JSONObject();
		try {
			if (StringUtil.isNotEmpty(classid)) {   // userId不为空 说明是修改
				classroom.setId(Integer.parseInt(classid));
				classService.updateClass(classroom);
				result.put("success", true);
			}else {   // 添加
				if(classService.existClassWithClassName(classroom.getClass_name())==null){  // 没有重复可以添加
					classService.addClass(classroom);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("deleteClass")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				classService.deleteClass(Integer.parseInt(id));
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
