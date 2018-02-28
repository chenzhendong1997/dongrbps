package dingzhen.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import dingzhen.entity.Leave;
import dingzhen.service.LeaveService;
import dingzhen.util.StringUtil;
import dingzhen.util.WriterUtil;


@Controller
@RequestMapping("leave")
public class LeaveController {

	private int page;
	private int rows;
	private Leave leave;
	@Autowired
	private LeaveService<Leave> leaveService;
	
	
	@RequestMapping("leave")
	public String index(HttpServletRequest request){
		if((Integer)request.getSession().getAttribute("roleId")==5) {
			return "leave";
		}else {
			return "adminleave";
		}
		
	}
	
	
	@RequestMapping("leaveList")
	public void leaveList(HttpServletRequest request,HttpServletResponse response){
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			Integer studentId=(Integer) request.getSession().getAttribute("userId");
			String userName=request.getParameter("userName");
			leave = new Leave();
			leave.setPage((page-1)*rows);
			leave.setRows(rows);
			JSONObject jsonObj = new JSONObject();
			if((Integer)request.getSession().getAttribute("roleId")==5) {
				leave.setStudentId(studentId);
				List<Leave> list= leaveService.findLeave(leave);
				int total = leaveService.countLeave(leave);
				jsonObj.put("total",total );
				jsonObj.put("rows", list);
			}else {
				leave.setUserName(userName);
				List<Leave> list= leaveService.findLeave(leave);
				int total = leaveService.countLeave(leave);
				jsonObj.put("total",total );
				jsonObj.put("rows", list);
			}
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveLeave")
	public void reserveUser(HttpServletRequest request,Leave leave,HttpServletResponse response){
		String userId = request.getParameter("userId");
		JSONObject result=new JSONObject();
		Integer studentId=(Integer) request.getSession().getAttribute("userId");
		try {
			if (StringUtil.isNotEmpty(userId)) {   // userId不为空 说明是修改
				leave.setId(Integer.parseInt(userId));
				leaveService.updateLeave(leave);
				result.put("success", true);
			}else {   // 添加
				leave.setStudentId(studentId);
				leaveService.addLeave(leave);
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
	
}
