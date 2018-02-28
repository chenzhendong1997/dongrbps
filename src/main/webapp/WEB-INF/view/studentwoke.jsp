<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>用户主页</title>
    
    <script type="text/javascript">

		// 关闭时清空表单
    	$(function(){
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
    		$('#dg').datagrid('load',{
    			userName:$("#s_userName").val(),
    		});
        }

		// 打开新增作业对话框
        function openUserAddDialog(){
        	$("#change").attr("style","display:none");
			$("#add").removeAttr("style");
        	var selectedRows=$("#dg").datagrid('getSelections');
        	var row=selectedRows[0];
        	$("#dlg").dialog("open").dialog("setTitle","新增作业");
        }
		
		// 打开修改用户信息对话框
        function openUserUpdateDialog(){
        	$("#add").attr("style","display:none");
			$("#change").removeAttr("style");
    		var selectedRows=$("#dg").datagrid('getSelections');
    		if(selectedRows.length!=1){
    			$.messager.alert('系统提示','请选择一条要编辑的数据！');
    			return;
    		}
    		var row=selectedRows[0];
    		$("#dlg").dialog("open").dialog("setTitle","修改作业");
    		$("#fm").form("load",row);
    	}
//打开上传作业窗口
        function openshangchuan(){
    		var selectedRows=$("#dg").datagrid('getSelections');
    		if(selectedRows.length!=1){
    			$.messager.alert('系统提示','请选择一条要编辑的数据！');
    			return;
    		}
    		var row=selectedRows[0];
    		$("#shangchuan").dialog("open").dialog("setTitle","上传作业");
    		$("#fm1").form("load",row);
    	}
        
        // xinzeng 作业信息
        function reserveAdd(){
        	var opt = $("#choosetype option:selected");
			var type = opt.val();
			url="reserveallwork.htm?type="+type+"";
        	$("#fm").form("submit",{
        		url:url,
    			onSubmit:function(){
    				return $(this).form("validate");
    			},
    			success:function(result){
    				var result=eval('('+result+')');
    				if(result.errorMsg){
    					$.messager.alert('系统提示',"<font color=red>"+result.errorMsg+"</1font>");
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
        	var opt = $("#check option:selected");
			var selflevel = opt.val();
			var selfscore = $("#score").val();
        	var selectedRows=$("#dg").datagrid('getSelections');
        	var row=selectedRows[0];
        	var assignId=row.id;
        	var type=row.type;
        	url="reservewokelevel.htm?selflevel="+selflevel+"&assignId="+assignId+"&selfscore="+selfscore+"&type="+type+"";
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
     
     // 保存上传
        function reserveshangchuan(){
        	url="insert.htm";
        	$("#fm1").form("submit",{
    			url:url,
    			onSubmit:function(){
    				return $(this).form("validate");
    			},
    			success:function(data){
    					closeshangchuan();
    					$("#dg").datagrid("reload");
    			}
    		});
        }

        // 关闭添加修改角色对话框
        function closeUserDialog(){
        	$("#fm").form('clear');
        	$("#dlg").dialog("close");
        }
        
     // 关闭
        function closeshangchuan(){
        	$("#fm1").form('clear');
        	$("#shangchuan").dialog("close");
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
   				 pagination="true" rownumbers="true" url="allhomeworkList.htm" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
                	<th data-options="field:'id',width:80" align="center">编号</th>
                	<th field="question" width="60" align="center">作业描述</th>
                	<th field="questionstandard" width="60" align="center">评分标准</th>
                	<th field="type" width="60" align="center">作业类型</th>
            	</tr>
        </thead>
</table>
    	
<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
		<privilege:operation operationId="22" clazz="easyui-linkbutton" onClick="openUserUpdateDialog()" name="更新作业完成情况"  iconCls="icon-edit" ></privilege:operation>
	</div>
	<div class="updownInterval"> </div>
	<privilege:operation operationId="22" clazz="easyui-linkbutton" onClick="openshangchuan()" name="上传作业"  iconCls="icon-edit" ></privilege:operation>
	<div>
	</div>
	<div class="updownInterval"> </div>
</div>




<!-- 新增和修改对话框 -->
<div id="dlg" class="easyui-dialog" style="text-align:right;width: 620px;height: 320px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
 <form id="fm" method="post">
 	<table cellspacing="5px;">
 		<tr>
  			<td>评分：</td>
  			<td><input type="hidden" id="roleId" name="roleId" /><input type="text" id="score" name="score"  class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>评级：</td>
  			<td>
	  			<select data-am-selected id="check">
					<option value="1" id="classid" name="classid" selected="selected">拓展练习</option>
					<option value="2" id="classid" name="classid">全部完成</option>
					<option value="3" id="classid" name="classid">基本完成</option>
					<option value="4" id="classid" name="classid">没有完成</option>
				</select>
			</td>
		</tr>
  	</table>
 </form>
 <div id="dlg-buttons" style="text-align:center">
	<a href="javascript:reserveUser()" class="easyui-linkbutton" iconCls="icon-ok"  id="change">保存</a>
	<a href="javascript:closeUserDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</div>

<!-- 上传作业 -->
<div id="shangchuan" class="easyui-dialog" style="text-align:right;width: 620px;height: 320px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
		<form method="post"
			enctype="multipart/form-data" id="fm1">
			<table>
				<tr>
					<td>选择作业:</td>
					<td><input type="file" name="file" /></td>
				</tr>
			</table>
		</form>
		<div id="dlg-buttons" style="text-align:center">
			<a href="javascript:reserveshangchuan()" class="easyui-linkbutton" iconCls="icon-ok"  id="change">保存</a>
			<a href="javascript:closeshangchuan()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
		</div>
	</div>

</body>
</html>
