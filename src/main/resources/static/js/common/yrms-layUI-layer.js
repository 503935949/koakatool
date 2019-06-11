/************************************************************************************
* 
* 项目名称：yueqingRMS   
* 类名称：yrms-layUI-layer.js   
* 类描述：YRMS、前端框架支持js
* 创建人：林曌   
* 创建时间：2017年7月10日 上午11:16:55   
* 修改人：   
* 修改时间：2017年7月10日 上午11:16:55   
* 修改备注：   
* @version  
*  
************************************************************************************/
/*---------------------------------------------------
 * 
 * 			全局初始化设置   start
 * 
 *--------------------------------------------------*/
/**
 * 全局开放初始化
 * @param options
 */
function commonDialogConfig(options) {
	layui.use('layer', function(){
		  var layer = layui.layer;
		  layer.config(options);
	}); 
}

/**
 * 疯狂模式，关闭所有层
 */
function layercloseAll() {
	//疯狂模式，关闭所有层
	layui.use('layer', function(){
		  var layer = layui.layer;
		  layer.closeAll() ;
	});
}
function layercloseAllType(type) {
	//疯狂模式，关闭所有层
	layui.use('layer', function(){
		  var layer = layui.layer;
		  layer.closeAll(type) ;
	});
}
/*---------------------------------------------------
 * 
 * 			全局初始化设置   end
 * 
 *--------------------------------------------------*/

/*---------------------------------------------------
 * 
 * 			提示弹窗   (type:0_默认)   start
 * 
 *--------------------------------------------------*/
function simpleTypeDialog (msg,ibody,lock,icon) {
	
	if(ibody == "parent" && lock == "lock") {
		parent.layerLockScrollDialog (msg,icon);
	} else if(ibody == "parent" && lock != "lock") {
		parent.layerSimpleDialog (msg,icon);
	} else if( lock == "lock") {
		layerLockScrollDialog (msg,icon);
	} else {
		layerSimpleDialog (msg,icon);
	}
		
}

/**
 * 简单提示弹窗
 * 
 */
function layerSimpleDialog(msg,icon) {
	layui.use('layer', function(){
		  var layer = layui.layer;
		//屏蔽浏览器滚动条
		  layer.open({
		  content: msg,
		  shadeClose:true,
		  time:5000,
		  icon:icon
		});
	}); 
}

/**
 * 简单提示弹窗（屏蔽浏览器滚动条）
 * 
 */
function layerLockScrollDialog (msg,icon) {
	layui.use('layer', function(){
		  var layer = layui.layer;
		//屏蔽浏览器滚动条
		  layer.open({
		  content: msg,
		  scrollbar: false,
		  icon:icon
		});
	}); 
}

/**
 * 错误提示弹窗（屏蔽浏览器滚动条）
 * 
 */
function errorDialog(msg) {
	if(msg==null||msg==undefined||msg==""){
		msg = "失败";
	}
	simpleTypeDialog(msg,'parent','lock','2');
	//simpleTypeDialog(response.responseText,'parent','lock','2');
}

/**
 * 警告提示弹窗（屏蔽浏览器滚动条）
 * 
 */
function warningDialog(msg) {
	simpleTypeDialog(msg,'parent','lock','3');
}

/**
 * 成功提示弹窗
 * 
 */
function successDialog(msg) {
	if(msg==null||msg==undefined||msg==""){
		msg = "成功";
	}
	layerSimpleDialog(msg,1); 
}
/*---------------------------------------------------
 * 
 * 			提示弹窗   (type:0_默认)   end
 * 
 *--------------------------------------------------*/

/*---------------------------------------------------
 * 
 * 			简单加载框   start
 * 
 *--------------------------------------------------*/
/**
 * 添加，加载框
 * 
 */
function addload () {
	layui.use('layer', function(){
		  var layer = layui.layer;
		  layer.load();
	});
}

/**
 * 关闭，加载框
 * 
 */
function closeload (index) {
	layui.use('layer', function(){
		  var layer = layui.layer;
		  layer.close(index); 
	});
}
/*---------------------------------------------------
 * 
 * 			简单加载框   end
 * 
 *--------------------------------------------------*/













