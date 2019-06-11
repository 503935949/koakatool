/************************************************************************************
* 
* 项目名称：yueqingRMS   
* 类名称：yrms-common.js   
* 类描述：YRMS、前端框架支持js
* 创建人：林曌   
* 创建时间：2017年7月10日 上午11:16:55   
* 修改人：   
* 修改时间：2017年7月10日 上午11:16:55   
* 修改备注：   
* @version  
*  
************************************************************************************/

function yrms() {
	this.tabBar = "tabsBar";
	this.locale = "zh_CN";
	this.sourceName = "message" ;
	this.staticTabs={};
	this.simpleTabs={};
	this.path  = "" ;
	this.language= "zh_CN" ;
		/* 建立左侧菜单方法 */	
	this.buildMenu_left = function (barId,data) {
			var menuBar = $("#"+barId);
			for(var i=0;i<data.length;i++) {
				var ilParent = $('<li class="layui-nav-item" ></li>').appendTo(menuBar);
				var node = data[i];
				var menu_head = $('<a href="#'+node.resourceId+'" data-toggle="collapse" class="collapsed"><i class="'+node.ico+'"></i> <span>'+node.resourceName+'</span> </a>').appendTo(ilParent);
				menu_head.attr("data_url",node.url?node.url:"javascript:;");
				menu_head.attr("data_tabId",node.resourceId);
				menu_head.attr("data_tabTitle",node.resourceName);
				//添加点击事件，改变iframe的url
				if(isNotEmpty(node.url)){
					menu_head.click(function(){
						
						var data_tabId = $(this).attr("data_tabId");
						var data_tabTitle = $(this).attr("data_tabTitle");
						var data_url = $(this).attr("data_url");
						if(!$.isEmptyObject(data_url)) {
							yrms.buildorChangeTabs (yrms.tabBar,data_tabId,data_tabTitle,data_url);
							//changeIframeSrc(page.iframe_Id,data_url);
							//timer = window.setInterval("reinitIframe()", 500);
						}else {
							
							if(!(typeof(node.chilNodes) != "undefined" && node.chilNodes.length>0)){
								simpleTypeDialog("功能开发中。。。",'parent','lock','0');
							}
							
						}
						
					});
				}
				
				//判断是否有子节点（可强行加标）
				if ( (typeof(node.chilNodes) != "undefined" && node.chilNodes.length>0)) {
//					var menu_parent_label = $('<i class="icon-submenu lnr lnr-chevron-left"></i>').appendTo(menu_head)
				}else{
					//判断是否加入徽章
					var exp = node.label;
					if (!$.isEmptyObject(exp)){
						//接入徽章
						var a_lv1_label = $('<span class="label label-info">'+ exp +'</span>').appendTo(menu_head);
					}
				}
				//再次判断子节点，layUI暂时支持两级菜单所以需要两级便利后进行递归
				if ( (typeof(node.chilNodes) != "undefined" && node.chilNodes.length>0)) {
					var dl = $('<dl class="layui-nav-child"> </dl>').appendTo(ilParent);
					for(var ci=0;ci<node.chilNodes.length;ci++) {
						var childnode = (node.chilNodes)[ci];
						var childmenu_head = $('<dd><a href="#'+childnode.resourceId+
								'" data-toggle="collapse" class="collapsed">'+
								'<i class="'+childnode.ico+'"></i>'+
								'<span>'+childnode.resourceName+'</span> </a></dd>').appendTo(dl);
						childmenu_head.attr("data_url",childnode.url?childnode.url:"javascript:;");
						childmenu_head.attr("data_tabId",childnode.resourceId);
						childmenu_head.attr("data_tabTitle",childnode.resourceName);
						//添加点击事件，改变iframe的url
						if(isNotEmpty(childnode.url)){
							childmenu_head.click(function(){
								
								var data_tabId = $(this).attr("data_tabId");
								var data_tabTitle = $(this).attr("data_tabTitle");
								var data_url = $(this).attr("data_url");
								if(!$.isEmptyObject(data_url)) {
									yrms.buildorChangeTabs (yrms.tabBar,data_tabId,data_tabTitle,data_url);
									//changeIframeSrc(page.iframe_Id,data_url);
									//timer = window.setInterval("reinitIframe()", 500);
								}else {
									
									if(!(typeof(childnode.chilNodes) != "undefined" && childnode.chilNodes.length>0)){
										simpleTypeDialog("功能开发中。。。",'parent','lock','0');
									}
									
								}
								
							});
						}
						//判断是否有子节点（可强行加标）
						if ( (typeof(childnode.chilNodes) != "undefined" && childnode.chilNodes.length>0)) {
//							var menu_parent_label = $('<i class="icon-submenu lnr lnr-chevron-left"></i>').appendTo(menu_head)
						}else{
							//判断是否加入徽章
							var exp = childnode.label;
							if (!$.isEmptyObject(exp)){
								//接入徽章
								var a_lv1_label = $('<span class="label label-info">'+ exp +'</span>').appendTo(childmenu_head);
							}
						}
						
						if ( (typeof(childnode.chilNodes) != "undefined" && childnode.chilNodes.length>0)) {
							var menu_body = $('<dd id="'+childnode.resourceId+'ChildMenuBar" ></dd>').appendTo(dl);
							yrms.buildMenu_left(childnode.resourceId+"ChildMenuBar",node.chilNodes);
						}
					}
				}
			}
		};
		this.buildorChangeTabs = function (lay_filter,data_tabId,data_tabTitle,data_url){
			
			layui.use('element', function(){
				  var $ = layui.jquery
				  ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
				  ifeame_id = data_tabId+"_iframe";
				  timer = window.setInterval("reinitIframe()", 500);
				  //判断是否已存在此ID的tab
				  var lis = $('ul.layui-tab-title').find('li');
				  var isEmpty = true;
				  has:for(var i=0;i<lis.length;i++) {
					  if($(lis[i]).attr("lay-id") == data_tabId) {
						   isEmpty = false;
						   break has;
					  }
				  }
				  //触发事件
				  if(isEmpty) {
					  //定时调用开始
					 // timer = window.setInterval("reinitIframe()", 500);
					  //新增一个Tab项
					  element.tabAdd(lay_filter, {
						title: data_tabTitle 
						,content:   '<iframe src="'+ data_url +'" '+ 
									'id="'+data_tabId+'_iframe" name="'+data_tabId+'_iframe" '+
									'frameBorder="0" scrolling="no" width="100%" '+
									'onLoad="IframeLoadEND();" ></iframe>'
						,id: data_tabId //实际使用一般是规定好的id
					  });
					  var simpleTab={
							  "id":data_tabId,
							  "name":data_tabTitle
					  }
					  yrms.simpleTabs[data_tabId]=simpleTab;
					  yrms.makeDelete(lay_filter);
					  yrms.tabChange(lay_filter, data_tabId); //切换到data_tabId
					  
				  } else {
					   //切换到指定Tab项
					  yrms.tabChange(lay_filter, data_tabId); //切换到
				  }
				  
				  //Hash地址的定位
				  //var layid = location.hash.replace(/^#tabsBar=/, '');
				  //element.tabChange(lay_filter, layid);
				  
				  //element.on('tab('+lay_filter+')', function(elem){
					//location.hash = lay_filter+'='+ $(this).attr('lay-id');
				 //});
				  
			});
					
		};
		this.OpenStaticTab = function (lay_filter,data_tabId,data_tabTitle,data_url){
			
			layui.use('element', function(){
				  var $ = layui.jquery
				  ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
				  ifeame_id = data_tabId+"_iframe";
				  timer = window.setInterval("reinitIframe()", 500);
				  //判断是否已存在此ID的tab
				  var lis = $('ul.layui-tab-title').find('li');
				  var isEmpty = true;
				  has:for(var i=0;i<lis.length;i++) {
					  if($(lis[i]).attr("lay-id") == data_tabId) {
						   isEmpty = false;
						   break has;
					  }
				  }
				  //触发事件
				  if(isEmpty) {
					  //定时调用开始
					 // timer = window.setInterval("reinitIframe()", 500);
					  //新增一个Tab项
					  element.tabAdd(lay_filter, {
						title: data_tabTitle 
						,content:   '<iframe src="'+ data_url +'" '+ 
									'id="'+data_tabId+'_iframe" name="'+data_tabId+'_iframe" '+
									'frameBorder="0" scrolling="no" width="100%" '+
									'onLoad="IframeLoadEND();" ></iframe>'
						,id: data_tabId //实际使用一般是规定好的id
					  });
				  } 
				  var staticTab={
						  "id":data_tabId,
						  "name":data_tabTitle
				  }
				  yrms.staticTabs[data_tabId]=staticTab;
				  yrms.tabChange(lay_filter, data_tabId); //切换到
				  //Hash地址的定位
				  //var layid = location.hash.replace(/^#tabsBar=/, '');
				  //element.tabChange(lay_filter, layid);
//				  <i class="layui-icon layui-unselect layui-tab-close">ဆ</i>
				  //element.on('tab('+lay_filter+')', function(elem){
					//location.hash = lay_filter+'='+ $(this).attr('lay-id');
				 //});
				  
			});
		};
		this.tabChange = function (lay_filter, data_tabId) {
			layui.use('element', function(){
				  var $ = layui.jquery
				  ,element = layui.element;
				element.tabChange(lay_filter, data_tabId);
				if($(window).width()<480){
                    $("#menuBarBody").addClass("menuBarBodyHide").removeClass("menuBarBodyShow");
				}
			});
		};
		this.makeDelete = function(lay_filter){
			var lis = $('ul.layui-tab-title').find('li');
			  for(var i=0;i<lis.length;i++) {
				  var id = $(lis[i]).attr("lay-id");
				  var tab = yrms.simpleTabs[id];
				  if(isNotEmpty(tab)) {
//					  tabDelete(lay_filter,obj)
					  $(lis[i]).html(tab.name+'<i onclick="tabDelete(this);" data-filter="'+lay_filter+'" data-id="'+id+'" class="layui-icon layui-unselect layui-tab-close">ဆ</i>');
				  }
			  }
		};
		this.getI18nResouse = function (sourceName,path,language,fun) {
			/* 需要引入 i18n 文件*/
	        if ($.i18n == undefined) {
	            console.log("请引入i18n js 文件")
	            return false;
	        };
	        /*
	        	这里需要进行i18n的翻译
	         */
	        jQuery.i18n.properties({
	            name : sourceName, //资源文件名称
	            path : path +'/', //资源文件路径
	            mode : 'map', //用Map的方式使用资源文件中的值
	            language : language,
	            callback : fun
	        });
		};
		
}
var yrms = new yrms();
//初始化框架对象
(function(){

})();
$(document).ready(function() {


    //var timer = window.setInterval("reinitIframe()", 500);

    //layUI _ layer 模态框组件全局初始化
    commonDialogConfig({
        resize:false //不可拉伸
        //skin: 'layui-layer-molv' //默认皮肤
    });

});

/* 修改iframe_src */
//function changeIframeSrc(iframeId,data_url) {
//	if(!$.isEmptyObject(data_url)) {
//		$("#"+iframeId).attr("src",data_url);
//		timer = window.setInterval("reinitIframe()", 500);
//	}
//}

/*-----------------------------------/
/*		iframe自适应调整 start
/*----------------------------------*/
//定时调用开始
var timer = window.setInterval("reinitIframe()", 500);
var ifeame_id ="";
//完毕后干掉定时器
function IframeLoadEND(){
	//修改外层DIV高度，适应内容
	var iframe = document.getElementById(ifeame_id);	
	try{
		window.clearInterval(timer);
		var bHeight = iframe.contentWindow.document.body.scrollHeight;
		var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
		var height = Math.max(bHeight, dHeight);
		iframe.height = height;
	}catch (ex){}
		// 停止定时
	window.clearInterval(timer);
	
}

// 定义一个函数，定时调用并刷新iframe高度
function reinitIframe(){
	
	//修改外层DIV高度，适应内容
	var iframe = document.getElementById(ifeame_id);
	try{
		var bHeight = iframe.contentWindow.document.body.scrollHeight;
		var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
		var height = Math.max(bHeight, dHeight);
		iframe.height = height;
	}catch (ex){
		
	}
};
/*-----------------------------------/
/*		iframe自适应调整end    */
/*----------------------------------*/


/*-----------------------------------/
/*	Ajax error function start
/*----------------------------------*/

//function XMLHttpRequestError (response) {
//	
//	if(errorDialogActon()){
//		if(xhr.responseType == "text") {
//			simpleTypeDialog(xhr.responseText,'parent','lock','2');
//		}else {
//			simpleTypeDialog(data,'parent','lock','2');
//		}
//		simpleTypeDialog(i18n_object_error,'parent','lock','2');
//	}
	
	// 返回一个失败状态的deferred对象，把错误代码作为默认参数传入之后fail()方法的回调
//	return $.Deferred().reject(resp.msg); 
//}
/**
 * 
 * 错误事件 
 *
 **/
function ajaxError(response) {
	
	if(errorDialogActon()){
		errorDialog(response);
	}
	
	// 返回一个失败状态的deferred对象，把错误代码作为默认参数传入之后fail()方法的回调
//	return $.Deferred().reject(resp.msg); 
}

/**
 * 
 * 成功事件 
 *
 **/
function ajaxSuccess() {
	successDialog();
}


function errorDialogActon () {
	
	//关闭所有layer窗口
	layercloseAll();
	return true;
}





/*-----------------------------------/
/*	Ajax error function end
/*----------------------------------*/



/*-----------------------------------/
/*	 isIE function start
/*----------------------------------*/


//ie6-8也是支持的
function isIE(){
	if (window.navigator.userAgent.indexOf("MSIE")>=1)
	return true;
	else
	return false;
}
//ie10及以上不支持ie浏览器的判断了，因为ie11已经不支持document.all了，下面是支持ie11的版本的，当然ie6-8也是支持的
function is_Ie() { //ie?
  if (!!window.ActiveXObject || "ActiveXObject" in window)
  return true;
  else
  return false;
 }


/*-----------------------------------/
/*	 isIE function end
/*----------------------------------*/



function isNotEmpty(str){
	if(str!=null&&str!=undefined&&str!=""){
		return true;
	}
	return false;
}


function tabDelete(obj){
	var id = $(obj).data("id");
	var filter = $(obj).data("filter");
	  layui.use('element', function(){
		  var $ = layui.jquery
		  ,element = layui.element;
		  element.tabDelete(filter, id);
	  });
}


