var consts = {}

$(function(){

    $("#getCardFaceInfo").on("click",function(e) {
        alert("开始操作拉取两个系统信息，点击确认 再次提示前请误操作");
        $.ajax({
            //type: "GET",
            url: "/attendance/getKoaKaInfo/",
            async:false,
            success: function(result) {
                $('#userGrid').bootstrapTable('refresh');
                alert("操作完成");
            },
            error:function(){
                alert("获得令牌失败，请确认网络连接正常，或登出其他用户");
            }
        });
    });

    $("#getFaceInfo").on("click", function() {
        alert("开始操作拉取人脸系统信息，点击确认 后请误做其他操作");

        $.ajax({
            type: "POST",
            url: "/faceinfo/insertFaceInfo",
            dataType: "json",
            data:JSON.stringify(datasss),
            contentType:"application/json",
            success: function(data) {
                console.log(data);
            },
            error: function (e) {
                console.log(e);
            }
        });

        $.ajax({
            type: "POST",
            url: "/faceinfo/getFaceLogs",
            dataType: "json",
            contentType:"application/json",
            success: function(result) {
                console.log(result);
                if (result.msg){
                    alert(result.msg);
                }
            },
            error: function (e) {
                console.log(e);
                alert("后台发生未知错误，可能网络连通失败，请查看日志");
            }
        });


    });





    $("#getCardInfo").on("click",function(e){
        alert("开始操作拉取门卡系统信息，点击确认 再次提示前后请误操作");
    	//获取id
		var identifier = null;
        $.ajax({
            type: "GET",
            url: "/cardinfo/getCardIdentifier/",
			async:false,
            success: function(result) {
                identifier = result.data;
                console.log(result);

            },
			error:function(){
            	alert("获得令牌失败，请确认网络连接正常，或登出其他用户");
			}
        });


        $.ajax({
            type: "GET",
            url: "https://47.92.132.65/ExosApi/log_api/v1.0/accessLogEntries",
            beforeSend: function (request) {
                request.setRequestHeader("Accept", "application/json; charset=utf-8");
                request.setRequestHeader("Authorization", "Basic "+identifier);
            },
			data:{
            	"$orderby":" LogDate desc",
				"$top":"10000"
			},
            success: function (result) {

				if(result.hasOwnProperty('value')){
                    var data = result.value;
                    console.log(data);
                    $.ajax({
                        type: "POST",
                        url: "/cardinfo/insertCordInfo/",
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        data: JSON.stringify(data),
                        success: function(result) {
                            console.log(result);
                            alert(result.msg);
                            $('#userGrid').bootstrapTable('refresh');
                        }
                    });

				}else{
					alert("拉取失败");
				}

            },
			complete:function() {
                //登出系统
                $.ajax({
                    type: "POST",
                    url: "/cardinfo/cardLogout/",
                    beforeSend: function (request) {
                        request.setRequestHeader("Accept", "application/json; charset=utf-8");
                        request.setRequestHeader("Authorization", "Basic "+identifier);
                    },
                    success: function(result) {
                        console.log(result);
                    }
                });
			}
        });
    });



    $( "#startTime" ).datepicker({
        defaultDate: "+1w",
        changeMonth: true,
        changeYear: true,
        numberOfMonths: 1,
        showAnim:"slideDown",
        //regional:"zh-CN",
        dateFormat:"yy-mm-dd",
        onClose: function( selectedDate ) {
            $( "#endTime" ).datepicker( "option", "minDate", selectedDate );
        }
    });
    $( "#endTime" ).datepicker({
        defaultDate: "+1w",
        changeMonth: true,
        changeYear: true,
        numberOfMonths: 1,
        showAnim:"slide",
        //regional:"zh-CN",
        dateFormat:"yy-mm-dd",
        onClose: function( selectedDate ) {
            $( "#startTime" ).datepicker( "option", "maxDate", selectedDate );
        }
    });

	bindUsersTable();
	//
	$("#userQuery").on("click",function(e){
		$('#userGrid').bootstrapTable('refresh');
	});

    $("#getCardInfoXls").on("click",function(e){
        var datas = $('#userGrid').bootstrapTable('getData');
        console.log(datas);
        // if(null==datas||datas.length==0){
        //     alert("请点击查询后导出");
        //     return false;
        // }
        if($( "#startTime" ).val()==""||$( "#startTime" ).val()==null||
            $( "#endTime" ).val()==""||$( "#endTime" ).val()==null){
            alert("请点选择时间区间");
            return false;
        }
        alert("开始导出信息，点击确认 如若长时间没有文件则查看LOG文件查看错误");
        downloadFile(contextPath+"/attendance/downLoadKoakaInfoXls?"+$("#searchForm").serialize());
    });
	//
    // $("#userReset").on("click",function() {
    //     $('#searchForm').each(function(){
    //         this.reset();
    //     });
    //     $('#userGrid').bootstrapTable('selectPage',1);
    // });

// 	$("#searchForm").on("reset",function(e){
// 		//e.preventDefault();
//
// 		$('#userGrid').bootstrapTable('selectPage',1);
// 		.bootstrapTable('getAllSelections');
//     	.bootstrapTable('getData');//所有data
//     	.bootstrapTable('getData',"useCurrentPage":true);//当前页data
// 	});
	

	
});


function bindUsersTable(){
	
	
	$('#userGrid').bootstrapTable({
        url: '/attendance/getAttendanceEvents',         				//请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        toolbar: '#template',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParams:function (params) { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
//        	console.log(params)
			var pageNum = 1;
//			if(params.offset==0){
//				pageNum = 1;
//			}else{
//				pageNum = ((params.offset)/params.limit) +1;
//			}
			pageNum = ((params.offset)/params.limit) +1;
//            return {
//                pageSize: params.limit, // 每页要显示的数据条数
//                page: pageNum, // 每页显示数据的开始行号
////                dataId: $("#dataId").val() // 额外添加的参数
//            }
			return $("#searchForm").serialize()+"&pageSize="+params.limit +"&pageNum="+ pageNum;
//          
        },
        responseHandler:function(response){
        	var data = {
        			"total" :response.total,
        			"rows" : response.list
        	}
        	return data;
        },
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10],        //可供选择的每页的行数（*）
//        search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: true,
        showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "userId",                     //每一行的唯一标识，一般为主键列
        showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
        icons: {
            paginationSwitchDown: 'glyphicon-collapse-down icon-chevron-down',
            paginationSwitchUp: 'glyphicon-collapse-up icon-chevron-up',
            refresh: 'glyphicon-refresh icon-refresh',
            toggle: 'glyphicon-list-alt icon-list-alt',
            columns: 'glyphicon-th icon-th',
            detailOpen: 'glyphicon-plus icon-plus',
            detailClose: 'glyphicon-minus icon-minus'
        },
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表onEditableSave
        columns: [{
	            checkbox: true
	        }, {
	            field: 'LogIdInternal',
	            title: '编号',
// 	            formatter:function(value, row, index){
// //	            	console.log(value);
// //	            	console.log(row);
// //	            	console.log(index);
// 	            	return '<a class="" data-toggle="modal" data-target="#showModal" data-unique-id="'+value+'" >'+value+'</a>';
// 	            }
	            
	        }, {
	            field: 'PersonalNr',
	            title: '工号'

	        }, {
                field: 'username',
                title: '姓名'
            }, {
	            field: 'DateStr',
	            title: '打卡日期'
	          
	        }, {
	            field: 'TimeStr',
	            title: '打卡时间'
	            
	        }
	        , {
	            field: 'logtype',
	            title: '拉取时间',
                formatter:function(value, row, index){
	                var msg;
                    if("1"==value){
                        msg = "签到";
                    }else if("2"==value){
                        msg = "签退";
                    }else{
                        msg = "未知";
                    }
                    return msg;
                }
	           
	        }

//	        , {
//	            field: 'operate',
//	            title: '操作',
//	            align: 'center',
//	            events: operateEvents,
//	            formatter: operateFormatter
//	        }
        
        
        ],onLoadSuccess: function(dataSource){  //加载成功时执行
        	console.log(dataSource)
            console.info("加载成功");
        },
        onLoadError: function(){  //加载失败时执行
              console.info("加载数据失败");
        }
//    	onEditableSave: function (field, row, oldValue, $el) {
//	         $.ajax({
//	             type: "post",
//	             url: "/edit",
//	             data: { strJson: JSON.stringify(row) },
//	             success: function (data, status) {
//	                 if (status == "success") {
//	                     alert("编辑成功");
//	                 }
//	             },
//	             error: function () {
//	                 alert("Error");
//	             },
//	             complete: function () {
//	
//	             }
//	
//	         });
//    	}
    });
	
	
	
	
};


function formValidata(documentId){
	var validatabody = $( "#"+documentId ).validate( {
			rules: {
				userId: "required",
				userName: {
					required: true,
					minlength: 2
				},
				sex: {
					required: true,
				},
				tel: {
					required: true,
					minlength: 5
				},
				email: {
					required: true,
					email: true
				},
				address: "required"
			},
			messages: {
				firstname1: "Please enter your firstname",
				lastname1: "Please enter your lastname",
				username1: {
					required: "Please enter a username",
					minlength: "Your username must consist of at least 2 characters"
				},
				password1: {
					required: "Please provide a password",
					minlength: "Your password must be at least 5 characters long"
				},
				confirm_password1: {
					required: "Please provide a password",
					minlength: "Your password must be at least 5 characters long",
					equalTo: "Please enter the same password as above"
				},
				email1: "Please enter a valid email address",
				agree1: "Please accept our policy"
			},
		});
	
	return validatabody;
}

//页面js创建Ifream方式下载文件
function downloadFile(url) {
    try{
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }catch(e){
    }
    // document.body.removeChild(elemIF);
}