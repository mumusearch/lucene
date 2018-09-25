<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>lucene数据库导入演示</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ext-all.css">
<script type="text/javascript">
var tableName;
var fieldName;
var searchResultGrid;
Ext.onReady(function(){
	//初始化表的下拉框
	function initTable(){
		var store=Ext.data.StoreManager.lookup('allTableStore');
	    store.load({
	    	params:{
	    		type:'getAlltable'
	    	},
	    	callback :function(records,operations,success){
	    		if(records.length>0){
	    			var record=records[0];
		    		//选中第一个
		    		var combo=Ext.getCmp('allTableCombobox');
		    		tableName=record.data.tableName;
		    		combo.setRawValue(tableName);
		    		combo.setValue(tableName);
		    		initTableField(tableName);
	    		}
	    	}
	    });
	};
	//获取表下的所有字段
	function initTableField(tableName){
		var store=Ext.data.StoreManager.lookup('allFieldStore');
		store.load({
	    	params:{
	    		type:'getAllField',
	    		tableName:tableName
	    	},
	    	callback :function(records,operations,success){
	    		if(records.length>0){
	    			var record=records[0];
		    		//选中第一个
		    		var combo=Ext.getCmp('allFieldCombobox');
		    		fieldName=record.data.fieldName;
		    		combo.setRawValue(fieldName);
		    		combo.setValue(fieldName);
	    		}
	    	}
	    });
	};
	//搜索
	function search(fieldName,fieldValue){
		var store=Ext.data.StoreManager.lookup('searchResultStore');
		store.load({
	    	params:{
	    		type:'search',
	    		tableName:tableName,
	    		fieldName:fieldName,
	    		fieldValue:fieldValue
	    	}
	    });
	}
	
	//所有的表数据源
	Ext.create('Ext.data.Store',{
		storeId:'allTableStore',
		fields: ['tableName'],
		autoLoad:false,
		proxy:{
			type :'ajax',
			url:'<%=request.getContextPath()%>/db/LuceneDBServlet.action',
			method:'post',
			reader:{
				type:'json'
			}
		}
	});
	//显示表的所有字段的数据源
	Ext.create('Ext.data.Store',{
		storeId:'allFieldStore',
		fields: ['fieldName'],
		autoLoad:false,
		proxy:{
			type :'ajax',
			url:'<%=request.getContextPath()%>/db/LuceneDBServlet.action',
			method:'post',
			reader:{
				type:'json'
			}
		}
	});
	//显示搜索结果数据源
	Ext.create('Ext.data.Store',{
		storeId:'searchResultStore',
		autoLoad:false,
		proxy:{
			type :'ajax',
			url:'<%=request.getContextPath()%>/db/LuceneDBServlet.action',
			method:'post',
			reader:{
				type:'json'
			}
		}
	});
	//导入表单
	var importFileForm=Ext.create('Ext.form.Panel',{
		title :'导入表',
		height:60,
		layout:'hbox',
		frame : true,
		items :[{
			id:'allTableCombobox',
			xtype :'combobox',
			queryMode:'local',
			name :'tableName',
			displayField:'tableName',
			valueField:'tableName',
			store:Ext.data.StoreManager.lookup('allTableStore'),
			listeners:{
				change:function(combobox,newValue,oldValue,options){
					tableName=newValue;
					if(oldValue){
					   initTableField(newValue);
					}
				}
			}
		},{
			xtype :'button',
			margin:'0 0 0 10',
			text :'加载',
			handler :function(btn){
				if(searchResultGrid){
					panel.removeAll();
				}
				Ext.Ajax.request({
					url:'<%=request.getContextPath()%>/db/LuceneDBServlet.action',
					method:'POST',
					async:false,
					params:{
						type:'load',
						tableName:tableName
					},
					success:function(response){
						searchResultGrid=Ext.JSON.decode(response.responseText);
						//panel.render('result');
						panel.add(searchResultGrid);
						Ext.getCmp('fieldValueId').setValue('');
						search(null,null);
					},
					failure:function(response){
					}
				});
			}
		}]
	});
	
	//搜索表单
	var searchForm=Ext.create('Ext.form.Panel',{
		frame : true,
		title :'搜索',
		layout :'column',
		height:70,
		items :[{
			xtype :'combobox',
			id:'allFieldCombobox',
			queryMode:'local',
			displayField:'fieldName',
			valueField:'fieldName',
			name :'fieldName',
			store:Ext.data.StoreManager.lookup('allFieldStore')
		},{
			id:'fieldValueId',
			xtype :'textfield',
			name :'fieldValue',
			//allowBlank:false
		},{
			xtype :'button',
			text :'搜索',
			handler :function(btn){
				//提交表单
				var values=this.up().getForm().getValues();
				search(values.fieldName,values.fieldValue);
			}
		}]
	});
	//搜索结果表格
	var searchResultGridPanel=Ext.create('Ext.grid.Panel',{
		frame : true,
		title:'搜索结果',
		id :'searchResultGridPanel',
		autoScroll:true,
		rowLines:true,
		columnLines:true,
		store:Ext.data.StoreManager.lookup('searchResultStore'),
		columns: [{ text: '文件名称',  dataIndex: 'fileName' ,flex:1},
		          { text: '文件大小',  dataIndex: 'size' ,flex:1},
		          { text: '文件类型',  dataIndex: 'type' ,flex:1}]
	});
	var panel=Ext.create('Ext.panel.Panel',{
    	renderTo:'result',
    	title :'结果显示',
    	frame:true
    });
    Ext.create('Ext.panel.Panel',{
    	renderTo:'search',
    	layout :{
    		type :'vbox',
    		align:'stretch'
    	},
    items :[importFileForm,searchForm]
    });
    //初始化
    initTable();
});
</script>
</head>
<body>
<div id='search'></div>
<div id='result'></div>
</body>
</html>