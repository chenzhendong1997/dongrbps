package dingzhen.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dingzhen.entity.Class;
import dingzhen.entity.Role;
import dingzhen.entity.User;
import dingzhen.service.ClassService;
import dingzhen.service.RoleService;
import dingzhen.service.UserService;
import dingzhen.util.HtmlUtil;


@Controller
@RequestMapping("chart")
public class ChartController {

	private User user;
	private Role role;
	@Autowired
	private UserService<User> userService;
	@Autowired
	private RoleService<Role> roleService;
	private Class classroom;
	@Autowired
	private ClassService<Class> classService;
	
	
	@RequestMapping("userchart")
	public String index(HttpServletRequest request,HttpServletResponse response){
			return "userchart";
	}
	@RequestMapping("classchart")
	public String classchart(HttpServletRequest request,HttpServletResponse response){
			return "classchart";
	}
	@RequestMapping("userchartList")
	private void echarts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");// 防止乱码
        response.setContentType("text/html;charset=utf-8");
        try {
        	role = new Role();
        	user = new User();
        	List<Role> list = roleService.findRole(role);
        	List<String> r=new ArrayList<>();
        	Map<String, Object> l = new HashMap<>();
        	List<Integer> c=new ArrayList<>();
        	for(int i=0;i<list.size();i++) {
        		r.add(list.get(i).getRoleName());
        		Integer rid=list.get(i).getRoleId();
        		user.setRoleId(rid);
        		c.add(userService.countUser(user));
        	}
            l.put("text", "用户表");
            l.put("legend_data", Arrays.asList("人数"));
            l.put("xAxis_data", r);
            l.put("series_name", "人数");
            l.put("series_data",c);
            l.put("series_type", "bar");
            HtmlUtil.writerJson(response, l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@RequestMapping("classchartList")
	private void classchartList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("111");
        request.setCharacterEncoding("UTF-8");// 防止乱码
        response.setContentType("text/html;charset=utf-8");
        try {
        	classroom = new Class();
        	user = new User();
        	List<Class> list= classService.findClass(classroom);
        	Map<String, Object> l = new HashMap<>();
        	for(int i=0;i<list.size();i++) {
        		l.put("name", list.get(i).getClass_name());
        		l.put("values",userService.countUser(user));
        	}
        	String [] str;
        		
        	System.out.println(l);
            HtmlUtil.writerJson(response, l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
