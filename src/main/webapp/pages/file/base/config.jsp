<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>索引配置</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ext-all.css">
<script type="text/javascript">
Ext.onReady(function(){
	var mapData=null;//集合配置
	var configNames=[];//配置名称集合
	var fieldArray=[];//字段集合
	var config=null;//配置信息
	//闭包获取配置集合
	(function(){
		var str=handleRequest('<%=request.getContextPath()%>/file/ConfigServlet.action', 'load', null);
		mapData=eval("(" + str + ')')
		//遍历配置集合 
		for(var configName in mapData){
			configNames.push({'configName':configName});
		}
	})();
	//初始化配置信息
	function init(configName){
		config=mapData[configName];
		//初始化基本配置信息
		Ext.getCmp('configNameCombobox').setValue(configName);
		Ext.getCmp('configNameCombobox').setRawValue(configName);
		basicConfigMessageForml.getForm().setValues(config);
		//初始化字段集合
		var fields=config['fields'];
		if(fields){
			for(var fieldName in fields){
				var obj=fields[fieldName];
				obj.fieldName=fieldName;
				fieldArray.push(obj);
			}
			fieldsGrid.getStore().loadData(fieldArray);
		}else{
			fieldArray=[];
			fieldsGrid.getStore().removeAll();
		}
	}
	//配置列表
	Ext.create('Ext.data.Store',{
		storeId:'configNamesComboboxStore',
		fields: ['configName'],
	    data:configNames
	});
	//分词器 列表
	Ext.create('Ext.data.Store',{
		storeId:'analyzerComboboxStore',
		fields: ['analyzerName', 'analyzerDesc'],
		autoLoad:{params:{type:'getAnalyzers'}},
	    proxy:{type:'ajax',url:'<%=request.getContextPath()%>/file/TokenStreamServlet.action',reader:{type:'json'}}
	});
	//搜索结果表格数据源
	Ext.create('Ext.data.Store',{
		storeId:'fieldsGridStore',
		fields:[{name:'fieldName'},
		        {name:'name'},
		        {name:'type'},
		        {name:'store'},
		        {name:'tokenized'},
		        {name:'indexed'}],
		data:[]
	});
	//域名下拉框数据源
	Ext.create('Ext.data.Store',{
		storeId:'fieldNameComboboxStore',
		fields: ['fieldName','fieldValue'],
	    data:[{fieldName:'id',fieldValue:'主键'},
	          {fieldName:'fileName',fieldValue:'文件名称'},
	          {fieldName:'type',fieldValue:'类型'},
	          {fieldName:'size',fieldValue:'大小'},
	          {fieldName:'content',fieldValue:'内容'},
	          {fieldName:'attribute',fieldValue:'属性'},
	          {fieldName:'createdate',fieldValue:'文件创建日期'},
	          {fieldName:'lasrmodifydate',fieldValue:'文件最后修改日期'},
	          {fieldName:'path',fieldValue:'路径'}]
	});
	
	//字段集合表格
	var fieldsGrid=Ext.create('Ext.grid.Panel',{
		padding:0,
		flex:1,
		autoScroll:true,
		rowLines:true,
		columnLines:true,
		store:Ext.data.StoreManager.lookup('fieldsGridStore'),
		columns:[{header:'域名',dataIndex:'fieldName',flex:1},
		         {header:'名称',dataIndex:'name',flex:1},
		         {header:'类型',dataIndex:'type',flex:1},
		         {header:'存储',dataIndex:'store',flex:1},
		         {header:'分词',dataIndex:'tokenized',flex:1},
		         {header:'索引',dataIndex:'indexed',flex:1}],
		bbar:['->',{
			text:'新增',
			handler:function(){
				var combobox=Ext.getCmp('fieldNameCombobox');
				combobox.setValue("");
				combobox.setRawValue("");
				combobox.setDisabled(false);
				
				var array=[];
				combobox.getStore().each(function(record){
					var fieldName=record.data.fieldName,exists=false;
					for(var i=0,len=fieldArray.length;i<len;i++){
						if(fieldArray[i].fieldName.toLowerCase()==fieldName.toLowerCase()){
							exists=true;
							break;
						}
					}
					if(!exists){
						array.push(record.data);
					}
				});
				combobox.getStore().loadData(array);	
				fieldWin.show();
			}
		},{
			text:'编辑',
			handler:function(){
				var selected=fieldsGrid.getSelectionModel().getSelection();
				if(selected&&selected.length>0){
					fieldForm.getForm().setValues(selected[0].data);
					Ext.getCmp('fieldNameCombobox').setDisabled(true);
					fieldWin.show();
				}
			}
		},{
			text:'删除',
			handler:function(){}
		},{
			text:'全部删除',
			handler:function(){}
		}],
		listeners:{
			itemdblclick:function(grid,record,item,index,e,eOpts){
				console.log(record.data);
				fieldForm.getForm().setValues(record.data);
				Ext.getCmp('fieldNameCombobox').setDisabled(true);
				fieldWin.show();
			}
		}
	});
	//字段表单
	var fieldForm=Ext.create('Ext.form.Panel',{
		flex:1,
		title:'字段检索属性',
		titleAlign:'center',
		frame:true,
		padding:0,
		defaults:{
			labelWidth:70,
			labelAlign:'right',
			margin:15,
			maxWidth:400
		},
		layout:{
			type:'vbox',
			align:'stretch'
		},
		items:[{
			id:'fieldNameCombobox',
			xtype:'combobox',
			fieldLabel:'域名',
			name:'fieldName',
        	queryMode: 'local',
	        displayField: 'fieldValue',
	        valueField: 'fieldName',
	        allowBlank:false,
	        store:Ext.data.StoreManager.lookup('fieldNameComboboxStore'),
		},{
			xtype      : 'fieldcontainer',
            fieldLabel : '存储',
            defaultType: 'radiofield',
            margin:15,
            layout: 'hbox',
            labelWidth:70,
            items: [
                {
                    boxLabel  : '是',
                    name      : 'store',
                    inputValue: 'true',
                    checked:true,
                    margin:'0 0 0 20'
                }, {
                    boxLabel  : '否',
                    name      : 'store',
                    inputValue: 'false',
                    margin:'0 0 0 20'
                }
            ]
		},{
			xtype      : 'fieldcontainer',
            fieldLabel : '分词',
            defaultType: 'radiofield',
            margin:15,
            layout: 'hbox',
            labelWidth:70,
            items: [
                {
                    boxLabel  : '是',
                    name      : 'tokenized',
                    inputValue: 'true',
                    checked:true,
                    margin:'0 0 0 20'
                }, {
                    boxLabel  : '否',
                    name      : 'tokenized',
                    inputValue: 'false',
                    margin:'0 0 0 20'
                }
            ]
		},{
			xtype:'textfield',
			fieldLabel:'索引',
			name:'indexed',
			xtype:'combobox',
        	queryMode: 'local',
	        displayField: 'indexedValue',
	        valueField: 'indexed',
	        allowBlank:false,
	        store:{
	        	storeId:'indexedComboboxStore',
	        	fields:['indexed','indexedValue'],
	        	data:[{indexed:'none',indexedValue:'不索引'},
	        	      {indexed:'docs',indexedValue:'索引'},
	        	      {indexed:'docs_and_freqs',indexedValue:'索引、频率'},
	        	      {indexed:'docs_and_freqs_and_positions',indexedValue:'索引、频率、位置增量'},
	        	      {indexed:'docs_and_freqs_and_positions_and_offsets',indexedValue:'索引、频率、位置增量、偏移量'}]
	        }
		}],
		bbar:['->',{
			text:'保存',
			handler:function(){
				if(fieldForm.getForm().isValid()){
					var configName=Ext.getCmp('configNameCombobox').getValue();
					fieldForm.getForm().submit({
						method:'POST',
						url:'<%=request.getContextPath()%>/file/ConfigServlet.action',
						params:{type:'update',configName:configName},
						success:function(form,action){
							
						},
						failure:function(form,action){
								
						}
					});
				}
			}
		},{
			text:'关闭',
			handler:function(){
				fieldWin.close();
			}
		},'->']
	});
	//字段集合弹框
	var fieldWin=Ext.create('Ext.window.Window',{
		width:500,
		height:350,
		closeAction:'hide',
		modal:true,
		padding:0,
		header:false,
		layout:{
			type:'vbox',
			align:'stretch'
		},
		items:[fieldForm]
	});
	//索引基本配置表单
	var basicConfigMessageForml=Ext.create('Ext.form.Panel',{
		padding:0,
		frame:true,
		flex:1,
		autoScroll:true,
		layout:{
			type:'vbox',
			align:'stretch'
		},
		items:[{
	        xtype:'fieldset',
	        flex:3,
	        padding:0,
	        title: '基本配置信息',
	        collapsible: false,
	        autoScroll:true,
	        layout: {
	        	type:'vbox',
	        	align:'stretch'
	        },
	        items :[{
	        	id:'configNameCombobox',
	        	xtype:'combobox',
	        	queryMode: 'local',
		        displayField: 'configName',
		        valueField: 'configName',
		        store:Ext.data.StoreManager.lookup('configNamesComboboxStore'),
				fieldLabel:'配置名称',
				labelAlig:'left',
				labelWidth:70,
				maxWidth:500,
				margin:15,
				listeners:{
					change:function(combo,newValue,oldValue,eOpts){
						if(oldValue){
						   init(newValue);
						}
					}
				}
			},{
				xtype:'combobox',
				fieldLabel:'版本号',
				name:'version',
				labelAlig:'left',
				labelWidth:70,
				maxWidth:500,
				margin:15,
				queryMode: 'local',
		        displayField: 'version',
		        valueField: 'version',
				store:{
					fields:[{name:'version'}],
					data:[{version:'5.5.1'}]
				}
			},{
				xtype:'textfield',
				fieldLabel:'索引位置',
				name:'directory',
				labelAlig:'left',
				labelWidth:70,
				maxWidth:500,
				margin:15
			},{
				xtype      : 'fieldcontainer',
	            fieldLabel : '索引类型',
	            defaultType: 'radiofield',
	            margin:15,
	            layout: 'hbox',
	            labelWidth:70,
	            items: [
	                {
	                    boxLabel  : '文件',
	                    name      : 'directory_type',
	                    inputValue: 'file',
	                    checked:true
	                }, {
	                    boxLabel  : '内存',
	                    name      : 'directory_type',
	                    inputValue: 'ram',
	                    margin:'0 0 0 20'
	                }
	            ]
			},{
				id:'analyzerCombobox',
	            fieldLabel: '分词器选择',
	            xtype:'combobox',
	            name: 'analyzer',
	            labelAlign:'left',
	            labelWidth:70,
	            queryMode: 'local',
	            displayField: 'analyzerName',
	            valueField: 'analyzerName',
	            store:Ext.data.StoreManager.lookup('analyzerComboboxStore'),
	            maxWidth:500,
	            margin:20
			}]
	    },{
	    	 xtype:'fieldset',
	    	 flex:5,
	    	 padding:0,
		     title: '字段集合',
		     collapsible: false,
		     layout: {
		        type:'vbox',
		        align:'stretch'
		     },
		     items :[fieldsGrid]   
	    }],
	    tbar:[{
	    	text:'新建',
	    	style: {
	            marginBottom: '10px',
	            'background-color':'white'
	        },
	    	handler:function(){
	    	}
	    },{
	    	text:'保存',
	    	style: {
	            marginBottom: '10px',
	            'background-color':'white',
	            margin:'0 0 0 20'
	        },
	    	handler:function(){
	    		
	    	}
	    }],
	    listeners:{
	    	afterrender:function(form){
	    		if(configNames.length>0){
	    			init(configNames[0].configName);
	    		}
	    	}
	    }
	});
	
	Ext.create('Ext.container.Viewport',{
    	layout :{
    		type :'vbox',
    		align:'stretch'
    	},
        items :[basicConfigMessageForml]
    });
});
</script>
</head>
<body>
</body>
</html>