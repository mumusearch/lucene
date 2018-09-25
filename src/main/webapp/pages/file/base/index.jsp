<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基本文件索引</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ext-all.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ext-all.css">
<script type="text/javascript">
Ext.onReady(function(){
	var tab=Ext.create('Ext.tab.Panel',{
		activeTab:0,
		flex:1,
		frame:true,
		padding:0,
		tabPosition:'top',
		title:'基本文件索引',
		titleAlign:'center',
		layout:{
			type:'vbox',
			align:'stretch'
		},
		items:[{
			title:'配置',
			html:'<iframe frameborder="0" width="100%" height="100%"  src="config.jsp"></iframe>'
		},{
			title:'创建索引',
			html:'<iframe frameborder="0" width="100%" height="100%"  src="createIndex.jsp"></iframe>'
		},{
			title:'搜索',
			html:'<iframe frameborder="0" width="100%" height="100%"  src="search.jsp"></iframe>'
		},{
			title:'分词',
			html:'<iframe frameborder="0" width="100%" height="100%"  src="analyzer.jsp"></iframe>'
		}]
	});
	
	Ext.create('Ext.container.Viewport',{
		padding:0,
		layout:{
			type:'vbox',
			align:'stretch'
		},
		items:[tab]
	});
});
</script>
</head>
<body>

</body>
</html>