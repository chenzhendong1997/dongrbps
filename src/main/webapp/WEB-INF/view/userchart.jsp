<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <div id="main" style="width: 600px;height:400px;"></div>
 <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        var option = {
        		   title : {
        			    text : ''
        			   },
        			   legend : {
        			    data : [ '' ]
        			   },
        			   xAxis : {
        			    data : []
        			   },
        			   yAxis : {},
        			   series : [ {
        			    name : '',
        			    type : 'bar',
        			    data : []
        			   } ]
        			  }

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        myChart.showLoading();// 加载动画
        $.ajax({
            url:"userchartList.htm",
            type:"POST",
            dataType:"json",
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',//防止乱码
            success:function(data){
//                 alert(data.text)
                myChart.hideLoading();
                var option = {
                		title: {
                         text: data.text
                     },
                     legend : {
                         data :  data.legend_data
                        },
                	    xAxis : {
                	        data : data.xAxis_data
                	       },
                	       series : [ {
                	        // 根据名字对应到相应的系列
                	        name : data.series_name,
                	        type : data.series_type,
                	        data : data.series_data
                	       } ]
                	      }

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });
    </script>
</body>
</html>
