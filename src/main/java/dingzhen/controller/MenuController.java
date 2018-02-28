package dingzhen.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dingzhen.entity.Menu;
import dingzhen.service.MenuService;
import dingzhen.util.StringUtil;
import dingzhen.util.WriterUtil;

@RequestMapping("menu")
@Controller
public class MenuController {

	private Menu menu;
	@Autowired
	private MenuService<Menu> menuService;
	@Autowired
	static Logger logger = Logger.getLogger(MenuController.class);
	
	
	@RequestMapping("menuIndex")
	public String index(){
		return "menu";
	}
	
	@RequestMapping("treeGridMenu")
	public void treeGridMenu(HttpServletRequest request,HttpServletResponse response){
		try {
			String parentId=request.getParameter("parentId");
			JSONArray jsonArray = getListByParentId(parentId);
			WriterUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public JSONArray getListByParentId(String parentId)throws Exception{
		JSONArray jsonArray=this.getTreeGridMenuByParentId(parentId);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			if("open".equals(jsonObject.getString("state"))){
				continue;
			}else{
				jsonObject.put("children", getListByParentId(jsonObject.getString("id")));
			}
		}
		return jsonArray;
	}
	
	
	
	public JSONArray getTreeGridMenuByParentId(String parentId)throws Exception{
		JSONArray jsonArray=new JSONArray();
		menu = new Menu();
		menu.setParentId(Integer.parseInt(parentId));
		List<Menu> list = menuService.findMenu(menu);
		for(Menu menu : list){
			JSONObject jsonObject = new JSONObject();
			Integer menuId = menu.getMenuId();
			jsonObject.put("id", menuId);
			jsonObject.put("text", menu.getMenuName());
			jsonObject.put("iconCls", menu.getIconCls());
			jsonObject.put("state", menu.getState());
			jsonObject.put("seq", menu.getSeq());
			jsonObject.put("menuUrl", menu.getMenuUrl());
			jsonObject.put("menuDescription", menu.getMenuDescription());
			
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	
	
	@RequestMapping("reserveMenu")
	public void reserveMenu(HttpServletRequest request,HttpServletResponse response,Menu menu){
		String menuId = request.getParameter("menuId");
		JSONObject result=new JSONObject();
		try {
			if (StringUtil.isNotEmpty(menuId)) {  //更新操作
				menu.setMenuId(Integer.parseInt(menuId));
				menuService.updateMenu(menu);
			} else {
				String parentId = request.getParameter("parentId");
				menu.setParentId(Integer.parseInt(parentId));
				if (isLeaf(parentId)) {
					// 添加操作
					menuService.addMenu(menu);  
					
					// 更新他上级状态。变成CLOSED
					menu = new Menu();
					menu.setMenuId(Integer.parseInt(parentId));
					menu.setState("closed");
					menuService.updateMenu(menu);
				} else {
					menuService.addMenu(menu);
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("菜单保存错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败！");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	
	// 判断是不是叶子节点
	public boolean isLeaf(String menuId){
		boolean flag = false;
		try {
			menu = new Menu();
			menu.setParentId(Integer.parseInt(menuId));
			List<Menu> list = menuService.findMenu(menu);
			if (list==null || list.size()==0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("判断菜单是否叶子节点错误",e);
		}
		return flag;
	}
	
	
	
	
	@RequestMapping("deleteMenu")
	public void deleteMenu(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try {
			String menuId = request.getParameter("menuId");
			String parentId = request.getParameter("parentId");
			if (!isLeaf(menuId)) {  //不是叶子节点，说明有子菜单，不能删除
				result.put("errorMsg", "该菜单下面有子菜单，不能直接删除");
			} else {
				menu = new Menu();
				menu.setParentId(Integer.parseInt(parentId));
				int sonNum = menuService.countMenu(menu);
				if (sonNum == 1) {  
					// 只有一个孩子，删除该孩子，且把父亲状态置为open
					menu = new Menu();
					menu.setMenuId(Integer.parseInt(parentId));
					menu.setState("open");
					menuService.updateMenu(menu);
					
					menuService.deleteMenu(Integer.parseInt(menuId));
				} else {
					//不只一个孩子，直接删除
					menuService.deleteMenu(Integer.parseInt(menuId));
				}
				result.put("success", true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("菜单删除错误",e);
			result.put("errorMsg", "对不起，删除失败！");
		}
		WriterUtil.write(response, result.toString());
	}
	
	 

}
