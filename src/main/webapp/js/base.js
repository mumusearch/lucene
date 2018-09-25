//禁用鼠标右键
document.oncontextmenu=function(){return false;}

/**
 * 发送Ajax请求
 * @param url 请求的url
 * @param type 请求的类型
 * @param value 请求的参数值
 */
function handleRequest(url,type,value){
	var result=null;
	Ext.Ajax.request({
		url:url,
		method:'POST',
		async:false,
		params:{
			type:type,
			value:value
		},
		success:function(response){
			result=response.responseText;
		},
		failure:function(response){
			Ext.MessageBox.alert('提示','请求失败');
		}
	});
	return result;
}
