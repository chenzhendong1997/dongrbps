package dingzhen.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONObject;
import dingzhen.entity.Attence;
import dingzhen.service.AttenceService;
import dingzhen.util.StringUtil;
import dingzhen.util.WriterUtil;


@Controller
@RequestMapping("attence")
public class AttenceController {

	private int page;
	private int rows;
	private Attence attence;
	@Autowired
	private AttenceService<Attence> attenceService;
	
	
	@RequestMapping("userattence")
	public String index(HttpServletRequest request,HttpServletResponse response){
		if((Integer)request.getSession().getAttribute("roleId")==5) {
			return "studentattence";
		}else {
			return "attence";
		}
	}
	
	
	@RequestMapping("attenceList")
	public void userList(HttpServletRequest request,HttpServletResponse response){
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			attence = new Attence();
			attence.setPage((page-1)*rows);
			attence.setRows(rows);
			attence.setTime(request.getParameter("time"));
			attence.setUserName(request.getParameter("userName"));
			JSONObject jsonObj = new JSONObject();
			if((Integer)request.getSession().getAttribute("userId")==1) {
				List<Attence> list= attenceService.findAttence(attence);
				int total = attenceService.countAttence(attence);
				jsonObj.put("total",total );
				jsonObj.put("rows", list);
			}
			else {
				attence.setUserId((Integer) request.getSession().getAttribute("userId"));
				List<Attence> list= attenceService.findAttence(attence);
				int total = attenceService.countAttence(attence);
				jsonObj.put("total",total );
				jsonObj.put("rows", list);
			}
			WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveAttence")
	public void reserveUser(HttpServletRequest request,Attence attence,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String score = request.getParameter("score");
			if (StringUtil.isNotEmpty(score)) {   // score不为空 说明是添加
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd");//格式化时间
				Date da=new Date();
				String ctime=df.format(da);
				attence.setTime(ctime);
				attence.setUserId(Integer.parseInt(request.getParameter("userId")));
				attenceService.addAttence(attence);
				result.put("success", true);
			}else {
				attence.setUserId(Integer.parseInt(request.getParameter("userId")));
				attence.setOne_check(request.getParameter("one_check"));
				attence.setTwo_check(request.getParameter("two_check"));
				attence.setThree_check(request.getParameter("three_check"));
				attence.setFour_check(request.getParameter("four_check"));
				attence.setFive_check(request.getParameter("five_check"));
				attence.setSix_check(request.getParameter("six_check"));
				attenceService.updateAttence(attence);
				result.put("success", true);
			}
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
//	
}
