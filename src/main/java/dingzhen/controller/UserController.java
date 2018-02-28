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
import dingzhen.entity.User;
import dingzhen.service.UserService;
import dingzhen.util.StringUtil;
import dingzhen.util.WriterUtil;


@Controller
@RequestMapping("user")
public class UserController {

	private int page;
	private int rows;
	private User user;
	@Autowired
	private UserService<User> userService;
	
	 @RequestMapping("export")
	    public @ResponseBody String export(HttpServletResponse response) {
	        response.setContentType("application/binary;charset=UTF-8");
	        try {
	            ServletOutputStream out = response.getOutputStream();
	            String fileName = new String(
	                    ("UserInfo " + new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getBytes(), "UTF-8");
	            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
	            export(out);
	            return "success";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "导出信息失败";
	        }
	    }
	 
	 private void export(ServletOutputStream out) throws Exception {
	        try {
	            // 第一步，创建一个workbook，对应一个Excel文件
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
	            HSSFSheet hssfSheet = workbook.createSheet("sheet1");
	            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
	            HSSFRow hssfRow = hssfSheet.createRow(0);
	            // 第四步，创建单元格，并设置值表头 设置表头居中
	            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
	            // 居中样式
	            hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

	            HSSFCell hssfCell = null;
	            String[] titles = { "用户编号", "用户名", "密码", "用户角色","备注","班级" };
	            for (int i = 0; i < titles.length; i++) {
	                hssfCell = hssfRow.createCell(i);// 列索引从0开始
	                hssfCell.setCellValue(titles[i]);// 列名1
	                hssfCell.setCellStyle(hssfCellStyle);// 列居中显示
	            }

	            // 第五步，写入实体数据
	            List<User> users= userService.findUser(user);

	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            // if (users != null && !users.isEmpty()) {
	            for (int i = 0; i < users.size(); i++) {
	                hssfRow = hssfSheet.createRow(i + 1);
	                User user = users.get(i);

	                // 第六步，创建单元格，并设置值
	                int userid = 0;
	                if (user.getUserId() != 0) {
	                    userid = user.getUserId();
	                }
	                hssfRow.createCell(0).setCellValue(userid);
	                String username = "";
	                if (user.getUserName() != null) {
	                    username = user.getUserName();
	                }
	                hssfRow.createCell(1).setCellValue(username);
	                String password = "";
	                if (user.getPassword() != null) {
	                    password = user.getPassword();
	                }
	                hssfRow.createCell(2).setCellValue(password);
	                String role = "";
	                if (user.getRoleName() != null) {
	                	role = user.getRoleName();
	                }
	                hssfRow.createCell(3).setCellValue(role);
	                String descript = "";
	                if (user.getUserDescription() != null) {
	                	descript = user.getUserDescription();
	                }
	                hssfRow.createCell(4).setCellValue(descript);
	                String banji = "";
	                if (user.getClass_name() != null) {
	                	banji = user.getClass_name();
	                }
	                hssfRow.createCell(5).setCellValue(banji);
	            }
	            // }

	            // 第七步，将文件输出到客户端浏览器
	            try {
	                workbook.write(out);
	                out.flush();
	                out.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new Exception("导出信息失败！");
	        }
	    }
	
	 @RequestMapping(value = "import", method = RequestMethod.POST)
	    public String batchimport(@RequestParam(value = "filename") MultipartFile file, HttpServletRequest request,
	            HttpServletResponse response, ModelMap model) throws IOException {
	        // 判断文件是否为空
	        if (file == null)
	            return null;
	        // 获取文件名
	        String name = file.getOriginalFilename();
	        // 进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
	        long size = file.getSize();
	        if (name == null || ("").equals(name) && size == 0)
	            return null;

	        // 批量导入。参数：文件名，文件。
	        boolean b = batchImport(name, file);
	        return "user";
	    }
	 
	 // 批量导入
	    private boolean batchImport(String name, MultipartFile file) {
	        boolean b = false;
	        try {
	            // 也可以用request获取上传文件
	            // MultipartFile fileFile = request.getFile("file"); //这里是页面的name属性
	            // 转换成输入流
	            InputStream is = file.getInputStream();
	            // 得到excel
	            HSSFWorkbook workbook = new HSSFWorkbook(is);
	            // 得到sheet
	            HSSFSheet sheet = workbook.getSheetAt(0);
	            // 得到行数
	            int firstRow = sheet.getFirstRowNum();
	            int lastRow = sheet.getLastRowNum();
	            for (int i = firstRow; i < lastRow + 1; i++) {

	                HSSFRow row = sheet.getRow(i);
	                int firstCell = row.getFirstCellNum();
	                int lastCell = row.getLastCellNum();

	                for (int j = firstCell; j < lastCell; j++) {

	                    HSSFCell cell = row.getCell(j);

	                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
	                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	                    }
	                    String val = cell.getStringCellValue();

	                    System.out.println(val);

	                }
	            }
	            // 做你需要的操作
	            b = true;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return b;
	    }
	
	@RequestMapping("userIndex")
	public String index(){
		return "user";
	}
	
	
	@RequestMapping("userList")
	public void userList(HttpServletRequest request,HttpServletResponse response){
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			user = new User();
			user.setPage((page-1)*rows);
			user.setRows(rows);
			user.setUserName(request.getParameter("userName"));
			String roleId = request.getParameter("roleId");
			if (StringUtil.isNotEmpty(roleId)) {
				user.setRoleId(Integer.parseInt(roleId));
			} else {
				user.setRoleId(null);
			}
			List<User> list= userService.findUser(user);
			int total = userService.countUser(user);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",total );
			jsonObj.put("rows", list);
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveUser")
	public void reserveUser(HttpServletRequest request,User user,HttpServletResponse response){
		String userId = request.getParameter("userId");
		JSONObject result=new JSONObject();
		try {
			if (StringUtil.isNotEmpty(userId)) {   // userId不为空 说明是修改
				user.setUserId(Integer.parseInt(userId));
				userService.updateUser(user);
				result.put("success", true);
			}else {   // 添加
				if(userService.existUserWithUserName(user.getUserName())==null){  // 没有重复可以添加
					userService.addUser(user);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("deleteUser")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				userService.deleteUser(Integer.parseInt(id));
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
