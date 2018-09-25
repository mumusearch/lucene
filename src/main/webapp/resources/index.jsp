<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务分析</title>
<link rel="stylesheet" type="text/css" href="resources/css/ext-all.css">
<script type="text/javascript" src="js/ext-all.js"></script>
<style type="text/css">
.x-panel-header
{
  text-align:center;
} 
</style>
<script type="text/javascript">
var iffId;//敌我属性的id
var forceListId;//部队列表的id
Ext.onReady(function(){
	//发送ajax请求
	function handleRequest(url,type,value){
		console.log(value);
		var success=true;
		Ext.Ajax.request({
			url : url,
			method:'POST',
			async:false,
			params:{
				type :type,
				value :value
			},
			success:function(response,options){
				var str=response.responseText;
				if(str=='success'){
					success=true;
					Ext.MessageBox.alert('信息提示','操作成功');
				}else if(str!=''){
					Ext.MessageBox.alert('信息提示',str);
				}else{
					Ext.MessageBox.alert('信息提示','操作失败');
				}
			},
			failure:function(response,options){
				Ext.MessageBox.alert('信息提示','操作失败');
			}
		});
		return success;
	}
	
	//作战限制数据模型
	Ext.define('operConstraintsModel',{
		extend:'Ext.data.Model',
		fields:[{name :'id',type :'string',mapping:'id'},
		        {name :'type',type :'string',mapping:'type'},
		        {name :'mark',type :'string',mapping:'mark'}]
	});
	//关键点数据模型
	Ext.define('keyPointModel',{
		extend:'Ext.data.Model',
		fields:[{name :'type',type :'string',mapping:'type'},
		        {name :'mark',type :'string',mapping:'mark'}]
	});
	
	//企图描述数据源
	Ext.create('Ext.data.Store', {
	    storeId:'attemptDescStore',
	    fields:['no', 'mark', 'attach'],
	    data:[{ 'no': 'Q1',  "mark":"企图1",  "attach":"555-111-1224"  },
	        { 'no': 'Q2',  "mark":"企图2",  "attach":"555-222-1234" },
	        { 'no': 'Q3', "mark":"企图3",  "attach":"555-222-1244"  },
	        { 'no': 'Q4', "mark":"企图4", "attach":"555-222-1254"  }]
	});
	//敌我属性数据源
	Ext.create('Ext.data.Store', {
	    storeId:'iffAttributeStore',
	    fields:['id', 'name'],
	    data:[
	        { 'id': '1',  "name":"我方" },
	        { 'id': '2',  "name":"敌方"},
	        { 'id': '3', "name":"友方"},
	        { 'id': '4', "name":"中立方"}
	    ]
	});
	//部队列表数据源
	Ext.create('Ext.data.Store', {
	    storeId:'forcesListStore',
	    fields:['id', 'name'],
	    data:[
	        { 'id': '1',  "name":"xxx作战集团" },
	        { 'id': '2',  "name":"xxx作战集团"},
	        { 'id': '3', "name":"xxx方"},
	        { 'id': '4', "name":"xxx方"}
	    ]
	});
	//作战限制数据源
	Ext.create('Ext.data.Store', {
	    storeId:'operConstraintsStore',
	    model:'operConstraintsModel',
	    autoLoad:{params:{type :'show'}},
	    proxy:{type :'ajax',url:'OperConstraintsServlet',method:'POST',reader:{type :'json'}}
	});
	//关键点数据源
	Ext.create('Ext.data.Store', {
	    storeId:'keyPointStore',
	    model:'keyPointModel',
	    autoLoad:{params:{type :'show'}},
	    proxy:{type :'ajax',url:'KeyPointServlet',method:'POST',reader:{type :'json'}}
	});
	
	//敌我属性表格
	var iffAttributeGridPanel=Ext.create('Ext.grid.Panel',{
		autoScroll:true,
		flex : 9,
		rowLines:false,
		store:Ext.data.StoreManager.lookup('iffAttributeStore'),
		hideHeaders:true,//隐藏标题栏
		columns: [{ text: '',  dataIndex: 'name' }]
	});
	//部队列表表格
	var forcesListGridPanel=Ext.create('Ext.grid.Panel',{
		autoScroll:true,
		flex : 9,
		rowLines:false,
		store:Ext.data.StoreManager.lookup('forcesListStore'),
		hideHeaders:true,//隐藏标题栏
		columns: [{ text: '',  dataIndex: 'name' }]
	});
	//企图描述表格
	var attemptDescGridPanel=Ext.create('Ext.grid.Panel',{
		autoScroll:true,
		flex : 9,
		store:Ext.data.StoreManager.lookup('attemptDescStore'),
		columns: [{ text: '序号',  dataIndex: 'no' },
		          { text: '内容描述', dataIndex: 'mark', flex: 1 },
		          { text: '关联附件', dataIndex: 'attach',flex : 1}]
	});
	//受领任务
	var delivery=Ext.create("Ext.form.Panel",{
		frame : true,
		autoScroll:true,
		flex : 4,
		title :'受领任务',
		layout: {
			type : 'vbox',
			align :'stretch'
		},
		items :[{
			xtype :'container',
			layout :'column',
			margin :10,
			flex :1,
			items :[{
				xtype :'displayfield',
				labelWidth :150,
				fieldLabel: '上级赋予的任务及其意图',
				columnWidth:'.9'
			},{
				flex :1,
				xtype :'filefield',
				buttonText :'导入...',
				maxWidth:30,
			}]
		},{
			xtype :'textareafield',
			margin :10,
			autoScroll:true,
			flex :9,
		}]
	});
	//各方企图判断
	var attemptJudge=Ext.create("Ext.panel.Panel",{
		frame : true,
		autoScroll:true,
		flex : 6,
		title: '各方企图判断',
		layout :{
			type :'hbox',
			align:'stretch'
		},
		items :[{
			xtype :'container',
			layout :{
				type:'vbox',
				align:'stretch'
			},
			flex :1,
			items :[{
				xtype :'displayfield',
				margin :'10 0 0 10',
				fieldLabel :'敌我属性',
			},iffAttributeGridPanel]
		},{
			xtype :'container',
			layout :{
				type:'vbox',
				align:'stretch'
			},
			flex :1,
			margin :'0 0 0 10',
			items :[{
				xtype :'displayfield',
				margin :'10 0 0 10',
				fieldLabel :'部队列表',
			},forcesListGridPanel]
		},{
			xtype :'container',
			flex :6,
			layout :{
				type:'vbox',
				align:'stretch'
			},
			margin :'0 0 0 10',
			items :[{
				xtype :'displayfield',
				margin :'10 0 0 10',
				fieldLabel :'企图描述',
			},attemptDescGridPanel] 
		}],
		buttons :[{
			xtype :'button',
			text :'添加'
		},{
			xtype :'button',
			text :'删除'
		},{
			xtype :'button',
			text :'全部删除'
		},{
			xtype :'button',
			text :'保存'
		}]
	});
	//作战限制
	var operConstraints=Ext.create("Ext.grid.Panel",{
		frame : true,
		autoScroll:true,
		columnLines:true,
		flex : 5,
		title: '作战限制',
		store:Ext.data.StoreManager.lookup('operConstraintsStore'),
		plugins: {
	        ptype: 'cellediting',
	        clicksToEdit: 2
	    },
		columns: [
		          { text: '限制类型',  dataIndex: 'type',flex: 1,editor:{xtype:'textfield'} },
		          { text: '限制描述', dataIndex: 'mark', flex: 1,editor:{xtype:'textfield'}}
		],
		rbar :[{
			xtype :'button',
			text :'添加',
			handler : function(btn){
				var store=this.up().up().getStore();
				store.add(new operConstraintsModel());
				this.up().up().getSelectionModel().select(store.last());
			}
		},{
			xtype :'button',
			text :'删除',
			handler :function(btn){
				var gridPanel=this.up().up(),store=gridPanel.getStore(),selected=gridPanel.getSelectionModel().getSelection();
				if(selected.length>0){
					Ext.MessageBox.confirm('信息提示','你确定要删除吗?',function(btn){
						if(btn=='yes'){
							var isAllPhantom=true,phantomModel=[],realModel=[],data=[];
							//遍历要删除的数据
							Ext.Array.each(selected,function(record){
								if(record.phantom){
									phantomModel.push(record);
								}else{
									isAllPhantom=false;
									realModel.push(record);
									data.push(record.data);
								}
							});
							//如果 不全是新添加的数据 则操作数据库 删除数据
							if(!isAllPhantom){
								var success=handleRequest('OperConstraintsServlet','delete',JSON.stringify(data));
								if(success){
									store.remove(realModel);
								}
							}
							store.remove(phantomModel);	
						}
					});
				}else{
					Ext.MessageBox.alert('信息提示','请选择一项删除');
				}
			}
		},{
			xtype :'button',
			text :'全部删除',
			handler :function(btn){
				var gridPanel=this.up().up(),store=gridPanel.getStore(),
				    data=[],isAllPhantom=true,phantomModel=[],realModel=[];
				if(store.getCount()>0){
					Ext.MessageBox.confirm('信息提示','你确定要全部删除吗?',function(btn){
						if(btn=='yes'){
							store.each(function(record){
								if(!record.phantom){
								   isAllPhantom=false;
								   realModel.push(record);
								   data.push(record.data);
								}else{
									phantomModel.push(record);
								}
							});
							if(!isAllPhantom){
								var success=handleRequest('OperConstraintsServlet','delete',JSON.stringify(data));
								if(success){
									store.remove(realModel);
								}
							}
							store.remove(phantomModel);
						}
					});
				}else{
					Ext.MessageBox.alert('信息提示','没有要删除的');
				}
			}
		},{
			xtype :'button',
			text :'保存',
			handler :function(btn){
				var store=this.up().up().getStore();
				var models=[],data=[];
				//遍历数据源
				store.each(function(record){
					if(record.dirty||record.phantom){
						models.push(record);
						data.push(record.data);
					}
				});
				if(models.length>0){
					var success=handleRequest('OperConstraintsServlet','update',JSON.stringify(data));
					if(success){
						store.reload();//保存或者更新成功 刷新数据源
					}
				}else{
					Ext.MessageBox.alert('信息提示','请添加或者修改一项,再来保存');
				}
			}
		}]
	});
	//关键点
	var keyPoint=Ext.create("Ext.grid.Panel",{
		frame : true,
		autoScroll:true,
		columnLines:true,
		flex : 5,
		title: '关键点',
		store:Ext.data.StoreManager.lookup('keyPointStore'),
		plugins: {
	        ptype: 'cellediting',
	        clicksToEdit: 2
	    },
		columns: [
		          { text: '关键点',  dataIndex: 'type',flex:1,editor:{xtype:'textfield'} },
		          { text: '关键点描述', dataIndex: 'mark',flex:1,editor:{xtype:'textfield'}}
		],
		rbar :[{
			xtype :'button',
			text :'添加',
			handler : function(btn){
				var store=this.up().up().getStore();
				store.add(new keyPointModel());
				this.up().up().getSelectionModel().select(store.last());
			}
		},{
			xtype :'button',
			text :'删除',
			handler :function(btn){
				var gridPanel=this.up().up(),store=gridPanel.getStore(),selected=gridPanel.getSelectionModel().getSelection();
				if(selected.length>0){
					Ext.MessageBox.confirm('信息提示','你确定要删除吗?',function(btn){
						if(btn=='yes'){
							var isAllPhantom=true,phantomModel=[],realModel=[],data=[];
							//遍历要删除的数据
							Ext.Array.each(selected,function(record){
								if(record.phantom){
									phantomModel.push(record);
								}else{
									isAllPhantom=false;
									realModel.push(record);
									data.push(record.data);
								}
							});
							//如果 不全是新添加的数据 则操作数据库 删除数据
							if(!isAllPhantom){
								var success=handleRequest('KeyPointServlet','delete',JSON.stringify(data));
								if(success){
									store.remove(realModel);
								}
							}
							store.remove(phantomModel);	
						}
					});
				}else{
					Ext.MessageBox.alert('信息提示','请选择一项删除');
				}
			}
		},{
			xtype :'button',
			text :'全部删除',
			handler :function(btn){
				var gridPanel=this.up().up(),store=gridPanel.getStore(),
				    data=[],isAllPhantom=true,phantomModel=[],realModel=[];
				if(store.getCount()>0){
					Ext.MessageBox.confirm('信息提示','你确定要全部删除吗?',function(btn){
						if(btn=='yes'){
							store.each(function(record){
								if(!record.phantom){
								   isAllPhantom=false;
								   realModel.push(record);
								   data.push(record.data);
								}else{
									phantomModel.push(record);
								}
							});
							if(!isAllPhantom){
								var success=handleRequest('KeyPointServlet','delete',JSON.stringify(data));
								if(success){
									store.remove(realModel);
								}
							}
							store.remove(phantomModel);
						}
					});
				}else{
					Ext.MessageBox.alert('信息提示','没有要删除的');
				}
			}
		},{
			xtype :'button',
			text :'保存',
			handler :function(btn){
				var store=this.up().up().getStore();
				var models=[],data=[];
				//遍历数据源
				store.each(function(record){
					if(record.dirty||record.phantom){
						models.push(record);
						data.push(record.data);
					}
				});
				if(models.length>0){
					var success=handleRequest('KeyPointServlet','update',JSON.stringify(data));
					if(success){
						store.reload();//保存或者更新成功 刷新数据源
					}
				}else{
					Ext.MessageBox.alert('信息提示','请添加或者修改一项,再来保存');
				}
			}
		}]
	});
	
	//界面搭建
	Ext.create("Ext.container.Viewport",{
		layout: {
			type : 'vbox',
			align :'stretch'
		},
		items :[{
			flex : 6,
			type :'panel',
			layout: {
				type : 'hbox',
				align :'stretch'
			},
			items :[delivery,attemptJudge]
		},{
			flex : 4,
			type :'panel',
			layout: {
				type : 'hbox',
				align :'stretch'
			},
			items :[operConstraints,keyPoint]
		}]
	});
});
</script>
</head>
<body>
</body>
</html>