<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>用户主页</title>
    
    <script type="text/javascript">

		// 关闭时清空表单
    	$(function(){
    		var value=$(".combo-value").val();
			$('#dlg').dialog({
		   	 	onClose:function(){
					//closeUserDialog();
		   	 		$("#fm").form('clear');
		   	 	}
			});
		});
    
		var url;
		// 条件搜索用户信息
		function searchUser(){
			$("#box").removeAttr("style");
			var value=$(".combo-value").val();
			$("#date").attr("value",value);
    		$('#dg').datagrid('load',{
    			time:$("#date").val(),
    		});
        }

		// 打开新增用户信息对话框
        function openUserAddDialog(){
        	$("#change").attr("style","display:none");
			$("#add").removeAttr("style");
        	var selectedRows=$("#dg").datagrid('getSelections');
    		if(selectedRows.length!=1){
    			$.messager.alert('系统提示','请选择您要新增考勤信息的人员！');
    			return;
    		}
    		var row=selectedRows[0];
    		$("#dlg").dialog("open").dialog("setTitle","新增考勤信息");
    		$("#fm").form("load",row);
    		$("#userName").attr("readonly","readonly");
        }
		
		// 打开修改对话框
        function openUserUpdateDialog(){
        	$("#add").attr("style","display:none");
			$("#change").removeAttr("style");
    		var selectedRows=$("#dg").datagrid('getSelections');
    		if(selectedRows.length!=1){
    			$.messager.alert('系统提示','请选择一条要编辑的数据！');
    			return;
    		}
    		var row=selectedRows[0];
    		$("#dlg").dialog("open").dialog("setTitle","修改考勤信息");
    		$("#fm").form("load",row);
    		$("#userName").attr("readonly","readonly");
    	}
		
		
        // 新增用户信息
        function reserveAdd(){
        	var opt1 = $("#onecheck option:selected");
    		var opt2 = $("#twocheck option:selected");
    		var opt3 = $("#threecheck option:selected");
    		var opt4 = $("#fourcheck option:selected");
    		var opt5 = $("#fivecheck option:selected");
    		var opt6 = $("#sixcheck option:selected");
			var one_check = opt1.val();
			var two_check = opt2.val();
			var three_check = opt3.val();
			var four_check = opt4.val();
			var five_check = opt5.val();
			var six_check = opt6.val();
			var selectedRows=$("#dg").datagrid('getSelections');
			var row=selectedRows[0];
    		var userId=row.userId;
    		url="reserveAttence.htm?one_check="+one_check+"&two_check="+two_check+"&three_check="+three_check+"&four_check="+four_check+"&five_check="+five_check+"&six_check="+six_check+"&userId="+userId+"&score=6";
        	$("#fm").form("submit",{
        		url:url,
    			onSubmit:function(){
    				return $(this).form("validate");
    			},
    			success:function(result){
    				var result=eval('('+result+')');
    				if(result.errorMsg){
    					$.messager.alert('系统提示',"<font color=red>"+result.errorMsg+"</font>");
    					return;
    				}else{
    					$.messager.alert('系统提示','保存成功');
    					closeUserDialog();
    					$("#dg").datagrid("reload");
    				}
    			}
    		});
        }

        // 保存用户信息
        function reserveUser(){
        	var selectedRows=$("#dg").datagrid('getSelections');
        	var row=selectedRows[0];
        	var userId=row.userId;
        	var opt1 = $("#onecheck option:selected");
    		var opt2 = $("#twocheck option:selected");
    		var opt3 = $("#threecheck option:selected");
    		var opt4 = $("#fourcheck option:selected");
    		var opt5 = $("#fivecheck option:selected");
    		var opt6 = $("#sixcheck option:selected");
			var one_check = opt1.val();
			var two_check = opt2.val();
			var three_check = opt3.val();
			var four_check = opt4.val();
			var five_check = opt5.val();
			var six_check = opt6.val();
        	url="reserveAttence.htm?one_check="+one_check+"&two_check="+two_check+"&three_check="+three_check+"&four_check="+four_check+"&five_check="+five_check+"&six_check="+six_check+"&userId="+userId+"";
        	$("#fm").form("submit",{
    			url:url,
    			onSubmit:function(){
    				return $(this).form("validate");
    			},
    			success:function(result){
    				var result=eval('('+result+')');
    				if(result.errorMsg){
    					$.messager.alert('系统提示',"<font color=red>"+result.errorMsg+"</font>");
    					return;
    				}else{
    					$.messager.alert('系统提示','保存成功');
    					closeUserDialog();
    					$("#dg").datagrid("reload");
    				}
    			}
    		});
        }

        // 关闭添加修改角色对话框
        function closeUserDialog(){
        	$("#fm").form('clear');
        	$("#dlg").dialog("close");
        }

		// 打开选择角色对话框
        function openRoleChooseDialog(){
        	$("#dlg2").dialog("open").dialog("setTitle","选择角色");
        }

		// 搜索角色
        function searchRole(){
    		$('#dg2').datagrid('load',{
    			roleName:$("#s_roleName").val()
    		});
    	}

		// 关闭选择角色对话框
    	function closeRoleDialog(){
    		$("#s_roleName").val("");
    		$('#dg2').datagrid('load',{
    			s_roleName:""
    		});
    		$("#dlg2").dialog("close");
    	}

    	// 选择角色
    	function chooseRole(){
    		var selectedRows=$("#dg2").datagrid('getSelections');
    		if(selectedRows.length!=1){
    			$.messager.alert('系统提示','请选择一个角色！');
    			return;
    		}
    		var row=selectedRows[0];
    		$("#roleId").val(row.roleId);
    		$("#roleName").val(row.roleName);
    		closeRoleDialog();
    	}

		//双击选中
    	$(function(){
    		$("#dg2").datagrid({
    			onDblClickRow:function(rowIndex,rowData){
    				chooseRole();
    			}
    		});
    	})
    	
    	// 删除用户
    	function deleteUser(){
    		var selectedRows=$("#dg").datagrid('getSelections');
    		if(selectedRows.length==0){
    			$.messager.alert('系统提示','请选择要删除的数据！');
    			return;
    		}
    		var strIds=[];
    		for(var i=0;i<selectedRows.length;i++){
    			strIds.push(selectedRows[i].userId);
    		}
    		var ids=strIds.join(","); 
    		$.messager.confirm("系统提示","您确认要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
    			if(r){
    				$.post("deleteUser.htm",{ids:ids},function(result){
    					if(result.success){
    						$.messager.alert('系统提示',"您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
    						$("#dg").datagrid("reload");
    					}else{
    						$.messager.alert('系统提示',result.errorMsg);
    					}
    				},"json");
    			}
    		});
    	}


		// 加载选择角色数据  并添加 请选择
    	$(function(){
        	var relation_id_sign = 0;
    		$("#s_roleId").combobox({
                url: '${path }/role/roleCombobox.htm',
                method: 'post',
                valueField: 'roleId',
                textField: 'roleName',
                editable: false,
                panelHeight: 'auto',
                onLoadSuccess: function() {
                    if (relation_id_sign == 0){
                        var data = $(this).combobox('getData');
                        data.unshift({'roleId':'','roleName':'-----全部-----'});
                        relation_id_sign++;
                        $("#s_roleId").combobox("loadData", data);//重新加载数据，且当 relation_id_sign==1时加载
                    }
                }
            });
        });
    </script>
    </head>
 
 
<body style="margin:1px">

<!-- 加载数据表格 -->
<table  id="dg" class="easyui-datagrid" fitColumns="true"
   				 pagination="true" rownumbers="true" url="attenceList.htm" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
            		<th field="userId" width="60" align="center">用户编号</th>
                	<th field="userName" width="60" align="center">用户名</th>
                	<th field="one_check" width="60" align="center">早晨</th>
                	<th field="two_check" width="60" align="center">午饭前</th>
                	<th field="three_check" width="60" align="center">午饭后</th>
                	<th field="four_check" width="60" align="center">晚饭前</th>
                	<th field="five_check" width="60" align="center">晚饭后</th>
                	<th field="six_check" width="60" align="center">晚自习</th>
                	<th field="score" width="60" align="center">成绩</th>
                	<th field="time" width="60" align="center">时间</th>
            	</tr>
        </thead>
</table>
    	
<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
	</div>
	<div class="updownInterval"> </div>
	<div>
		&nbsp;时间：&nbsp;<input class="easyui-datebox"  data-options="formatter:myformatter,parser:myparser" id="date"></input>
		
		<a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
	</div>
	<div class="updownInterval"> </div>
</div>




<!-- 修改对话框 -->
<div id="dlg" class="easyui-dialog" style="text-align:right;width: 620px;height: 320px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
 <form id="fm" method="post">
 	<table cellspacing="5px;">
  		<tr>
  			<td>用户名：</td>
  			<td><input type="text" id="userName" name="userName" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>早晨：</td>
  			<td><select data-am-selected  id="onecheck">
				<option value="1" id="classid" name="classid">正常</option>
				<option value="0" id="classid" name="classid">请假</option>
				<option value="2" id="classid" name="classid">迟到</option>
				<option value="3" id="classid" name="classid">缺勤</option>
				</select>
			</td>
		</tr>
		<tr>
  			<td>午饭前：</td>
  			<td><select data-am-selected  id="twocheck">
				<option value="1" id="classid" name="classid" selected="selected">正常</option>
				<option value="0" id="classid" name="classid">请假</option>
				<option value="2" id="classid" name="classid">迟到</option>
				<option value="3" id="classid" name="classid">缺勤</option>
				</select>
			</td>
		</tr>
		<tr>
  			<td>午饭后：</td>
  			<td><select data-am-selected  id="threecheck">
				<option value="1" id="classid" name="classid" selected="selected">正常</option>
				<option value="0" id="classid" name="classid">请假</option>
				<option value="2" id="classid" name="classid">迟到</option>
				<option value="3" id="classid" name="classid">缺勤</option>
				</select>
			</td>
		</tr>
		<tr>
  			<td>晚饭前：</td>
  			<td><select data-am-selected  id="fourcheck">
				<option value="1" id="classid" name="classid" selected="selected">正常</option>
				<option value="0" id="classid" name="classid">请假</option>
				<option value="2" id="classid" name="classid">迟到</option>
				<option value="3" id="classid" name="classid">缺勤</option>
				</select>
			</td>
		</tr>
		<tr>
  			<td>晚饭后：</td>
  			<td><select data-am-selected  id="fivecheck">
				<option value="1" id="classid" name="classid" selected="selected">正常</option>
				<option value="0" id="classid" name="classid">请假</option>
				<option value="2" id="classid" name="classid">迟到</option>
				<option value="3" id="classid" name="classid">缺勤</option>
				</select>
			</td>
		</tr>
		<tr>
  			<td>晚自习：</td>
  			<td><select data-am-selected  id="sixcheck">
				<option value="1" id="classid" name="classid" selected="selected">正常</option>
				<option value="0" id="classid" name="classid">请假</option>
				<option value="2" id="classid" name="classid">迟到</option>
				<option value="3" id="classid" name="classid">缺勤</option>
				</select>
			</td>
		</tr>
  	</table>
 </form>
</div>

<div id="dlg-buttons" style="text-align:center">
	<a href="javascript:reserveUser()" class="easyui-linkbutton" iconCls="icon-ok"  id="change">保存</a>
	<a href="javascript:reserveAdd()" class="easyui-linkbutton" iconCls="icon-ok"  id="add">确认增加</a>
	<a href="javascript:closeUserDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>


<!-- 用户角色对话框 -->
<div id="dlg2" class="easyui-dialog" iconCls="icon-search" style="width: 500px;height: 480px;padding: 10px 20px"
  closed="true" buttons="#dlg2-buttons">
  <div style="height: 40px;" align="center">
  	角色名称：<input type="text" id="s_roleName" name="s_roleName" onkeydown="if(event.keyCode==13) searchRole()"/>
  	<a href="javascript:searchRole()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
  </div>
  <div style="height: 350px;">
  	<table id="dg2" title="查询结果" class="easyui-datagrid" fitColumns="true"  pagination="true" rownumbers="true" url="${path }/role/roleList.htm" singleSelect="true" fit="true" >
    <thead>
    	<tr>
    		<th field="roleId" width="50" align="center">编号</th>
    		<th field="roleName" width="100" align="center">角色名称</th>
    		<th field="roleDescription" width="200" align="center">备注</th>
    	</tr>
    </thead>
</table>
  </div>
</div>

<div id="dlg2-buttons">
	<a href="javascript:chooseRole()" class="easyui-linkbutton" iconCls="icon-ok" >确定</a>
	<a href="javascript:closeRoleDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>
<script type="text/javascript">
		function myformatter(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		}
		function myparser(s){
			if (!s) return new Date();
			var ss = (s.split('-'));
			var y = parseInt(ss[0],10);
			var m = parseInt(ss[1],10);
			var d = parseInt(ss[2],10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		}
	</script>
</body>
</html>
