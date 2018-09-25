<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ext-all.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ext-all.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/base.js">
<title>创建索引</title>
<script type="text/javascript">
Ext.onReady(function(){
	//表单
	var form=Ext.create('Ext.form.Panel',{
		titleAlign:'center',
		frame:true,
		layout:{
			type:'hbox'
		},
		items:[{
			xtype:'hiddenfield',
			id:'isMultipartContent',
			name:'isMultipartContent',
			value:'false'
		},{
			xtype:'textfield',
			fieldLabel:'文件',
			id:'fileNameId',
			labelWidth:60,
			name:'directory',
			//labelAlign:'right',
			allowBlank:false,
			width:400,
			margin:10
		},{
			xtype:'filefield',
			buttonText:'选择文件',
			width:0,
			margin:10,
			listeners:{
				change:function(field,value,eOpts){
					if(value!=null){
						while(value.indexOf('\\')>-1){
							value=value.replace('\\','/');
						}
						var lastIndexOf=value.lastIndexOf('/');
						if(lastIndexOf>-1){
							value=value.substring(lastIndexOf+1);
						}
						Ext.getCmp('fileNameId').setValue(value);
						Ext.getCmp('isMultipartContent').setValue("true");
					}
				}
			}
		},{
			xtype:'button',
			text:'提交',
			width:70,
			margin:'10 10 10 100',
			handler:function(){
				if(form.getForm().isValid()){
					var directory=Ext.getCmp('fileNameId').getValue();
					var isMultipartContent=Ext.getCmp('isMultipartContent').getValue();
					form.getForm().submit({
						method:'POST',
						url:'<%=request.getContextPath()%>/file/BaseFileServlet.action?type=addIndex&directory='+directory+'&isMultipartContent='+isMultipartContent,
						success: function(form, action) {
							var result=action.result.result;
							while(result.indexOf('/n')>-1){
								result=result.replace('/n','<br/>');
							}
							var el=document.getElementById('indexPanel');
							document.getElementById('indexPanel').innerHTML=result;
							Ext.getCmp('isMultipartContent').setValue("false");
		                },
		                failure: function(form, action) {
		                	var el=document.getElementById('indexPanel');
							document.getElementById('indexPanel').innerHTML="索引结果：<br/>the request was rejected because its size (26756018) exceeds the configured maximum (2097152)";
							Ext.getCmp('isMultipartContent').setValue("false");
		                }
					});
				}
			}
		}]
	});
	//索引创建过程显示panel
	var indexPanel=Ext.create('Ext.panel.Panel',{
		frame:true,
		flex:1,
		html:"<div id='indexPanel'>索引结果：</div>"
	});
	
	Ext.create('Ext.container.Viewport',{
		layout:{
			type:'vbox',
			align:'stretch'
		},
		items:[form,indexPanel]
	});
});
</script>
</head>
<body>
</body>
</html>