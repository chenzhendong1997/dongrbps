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
    			roleId:$('#s_roleId').combobox("getValue")
    		});
        }
		
		function check (){
			  var excel_file = $("#excel_file").val();  
		      if (excel_file == "" || excel_file.length == 0) {  
		          alert("请选择文件路径！");  
		          return false;  
		      } else {  
		         return true;  
		      }  
		}

		// 打开新增用户信息对话框
        function openUserAddDialog(){
        	$("#dlg").dialog("open").dialog("setTitle","添加用户信息");
        	$("#userName").removeAttr("readonly");
        	url = 'reserveUser.htm';
        }
		
		// 打开修改用户信息对话框
        function openUserUpdateDialog(){
    		var selectedRows=$("#dg").datagrid('getSelections');
    		if(selectedRows.length!=1){
    			$.messager.alert('系统提示','请选择一条要编辑的数据！');
    			return;
    		}
    		var row=selectedRows[0];
    		$("#dlg").dialog("open").dialog("setTitle","填写问卷");
    		var id =row.id;
    		$.ajax({
		          url: "showquestion.htm",
		          type:"POST",
		          data : "id="+id+"",
		          dataType:"json",
		          contentType: 'application/x-www-form-urlencoded; charset=UTF-8',//防止乱码
		          success:function(data){
		        	  var s ="";
				        for(var i = 0;i<data.length;i++){
				            var first = data[i];
				            s+=("<div id='choose'>"+first.question+"</br><input type='radio' name='"+first.id+"' value='第"+first.id+"题选了"+first.one+"'>"+first.one+"<input type='radio' name='"+first.id+"' value='"+first.two+"'>"+first.two+"<input type='radio' name='"+first.id+"' value='"+first.three+"'>"+first.three+"<input type='radio' name='"+first.id+"' value='"+first.four+"'>"+first.four+"</div>"); 
				        }
				        	$("#fm").html(s);
		          }
		        });
    	}

        // 保存用户信息
        function reserveUser(){
        	var selectedRows=$("#dg").datagrid('getSelections');
        	var row=selectedRows[0];
        	var testId =row.id;
        	$.ajax({
		          url: "showquestion.htm",
		          type:"POST",
		          data : "id="+testId+"",
		          dataType:"json",
		          contentType: 'application/x-www-form-urlencoded; charset=UTF-8',//防止乱码
		          success:function(data){
		        	  var questionanswer ="";
				        for(var i = 0;i<data.length;i++){
				            var first = data[i];
				            questionanswer+= $("input[name='"+first.id+"']:checked").val()+",";
				        }
				            submitchoose(questionanswer);
		          }
		        });
        }
        
        function submitchoose(questionanswer) {
        	var selectedRows=$("#dg").datagrid('getSelections');
        	var row=selectedRows[0];
        	var testId =row.id;
        	url="submitquestion.htm?testId="+testId+"&questionanswer="+questionanswer+"";
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
    					$.messager.alert('系统提示','提交成功');
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

    </script>
    </head>
 
 
<body style="margin:1px">

<!-- 加载数据表格 -->
<table  id="dg" class="easyui-datagrid" fitColumns="true"
   				 pagination="true" rownumbers="true" url="questionList.htm" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
                	<th data-options="field:'id',width:40" align="center">编号</th>
                	<th data-options="field:'userName',width:80" align="center">问卷创建人</th>
                	<th field="questionname" width="60" align="center">问卷名</th>
            	</tr>
        </thead>
</table>
    	
<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
		<privilege:operation operationId="10004" clazz="easyui-linkbutton" onClick="openUserUpdateDialog()" name="填写问卷"  iconCls="icon-edit" ></privilege:operation>
	</div>
	<div class="updownInterval"> </div>
	<div class="updownInterval"> </div>
</div>

<!-- 展示问卷 -->
<div id="dlg" class="easyui-dialog" style="text-align:right;width: 620px;height: 320px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
 <form id="fm" method="post">
 
 </form>
</div>
<div id="dlg-buttons" style="text-align:center">
	<a href="javascript:reserveUser()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closeUserDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>


<div id="dlg2-buttons">
	<a href="javascript:chooseRole()" class="easyui-linkbutton" iconCls="icon-ok" >确定</a>
	<a href="javascript:closeRoleDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

</body>
</html>
