<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分词</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ext-all.css">
<script type="text/javascript">
Ext.onReady(function(){
	//分词器 列表
	Ext.create('Ext.data.Store',{
		storeId:'analyzerComboboxStore',
		fields: ['analyzerName', 'analyzerDesc'],
		autoLoad:{params:{type:'getAnalyzers'}},
	    proxy:{type:'ajax',url:'<%=request.getContextPath()%>/file/TokenStreamServlet.action',reader:{type:'json'}},
	    listeners:{
	    	load:function(store,records,opts){
	    		if(records&&records.length>0){
	    			var record=records[0];
	    			var combobox=Ext.getCmp('analyzerCombobox');
	    			combobox.setValue(record.data.analyzerName);
	    			combobox.setRawValue(record.data.analyzerName);
	    			
	    			Ext.getCmp('analyzerDesc').setValue(record.data.analyzerDesc);
	    		}
	    	}
	    }
	});
	//分词form表单
	var analyzerForml=Ext.create('Ext.form.Panel',{
		padding:0,
		flex:1,
		layout: {
        	type:'vbox',
        	align:'stretch'
        },
		items:[{
	        xtype:'fieldset',
	        flex:3,
	        title: '分词器',
	        collapsible: false,
	        layout: {
	        	type:'vbox',
	        	align:'stretch'
	        },
	        items :[{
	        	id:'analyzerCombobox',
	            fieldLabel: '分词器选择',
	            xtype:'combobox',
	            name: 'analyzerName',
	            labelAlign:'left',
	            labelWidth:70,
	            queryMode: 'local',
	            displayField: 'analyzerName',
	            valueField: 'analyzerName',
	            store:Ext.data.StoreManager.lookup('analyzerComboboxStore'),
	            maxWidth:400,
	            height:20,
	            listeners:{
	            	select:function(combo,records,eOpts){
	            		var record=records[0];
	            		Ext.getCmp('analyzerDesc').setValue(record.data.analyzerDesc);
	            	}
	            }
	        }, {
	        	xtype:"textareafield",
	            fieldLabel: '分词器简介',
	            id:'analyzerDesc',
	            name: 'analyzerDesc',
	            labelWidth:70,
	            labelAlign:'left'
	        }]
	    },{
	    	xtype:'fieldset',
	    	flex:3,
	        title: '输入短语',
	        collapsible: false,
	        layout: {
	        	type:'vbox',
	        	align:'stretch'
	        },
	        items :[{
	        	xtype:"textareafield",
	            name: 'inputValue',
	            id:'inputValue',
	            flex:1,
	            value:'中华人民共和国万岁万万岁',
	        },{
	        	xtype:'button',
	        	text:'分解短语',
	        	maxWidth:100,
	        	handler:function(){
	        		var inputValue=Ext.getCmp('inputValue').getValue();
	        		var analyzerName=Ext.getCmp('analyzerCombobox').getValue();
	        		if(inputValue==null||inputValue==''){
	        			Ext.MessageBox.alert('提示','请输入短语');
	        		}else if(analyzerName==null||analyzerName==''){
	        			Ext.MessageBox.alert('提示','请选择分词器');
	        		}else{
	        			var result=handleRequest("<%=request.getContextPath()%>/file/TokenStreamServlet.action", "tokenStream", "{'inputValue':'"+inputValue+"','analyzerName':'"+analyzerName+"'}");
	        			var oldValue=Ext.getCmp('result').getValue();
	        			if(oldValue==null||oldValue==''){
	        				Ext.getCmp('result').setValue(result);
	        			}else{
	        				Ext.getCmp('result').setValue(oldValue+'\n'+result);
	        			}
	        		}
	        	}
	        }]
	    },{
	    	xtype:'fieldset',
	    	flex:6,
	        title: '语汇单元',
	        collapsible: false,
	        layout: {
	        	type:'vbox',
	        	align:'stretch'
	        },
	        items :[{
	        	xtype:'textareafield',
	            name: 'result',
	            id:'result',
	            flex:1
	        }]
	    }]
	});
	Ext.create('Ext.container.Viewport',{
    	layout :{
    		type :'vbox',
    		align:'stretch'
    	},
        items :[analyzerForml]
    });
});
</script>
</head>
<body>

</body>
</html>