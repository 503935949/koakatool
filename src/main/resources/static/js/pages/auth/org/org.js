var consts = {}

$(function(){



	bindOrgsTable();
	//
	$("#orgQuery").on("click",function(e){
		$('#orgGrid').bootstrapTable('refresh');
	});


    $('#insertModal').on('show.bs.modal', function () {
        // 执行一些动作...
        clearAdd();
    });
    $("#insertReset").on("click",function(e){
        clearAdd();
    });


    $("#orgReset").on("click",function() {
        $('#searchForm').each(function(){
            this.reset();
        });
        $('#orgGrid').bootstrapTable('selectPage',1);
    });

	$("#searchForm").on("reset",function(e){
		//e.preventDefault();

		$('#orgGrid').bootstrapTable('selectPage',1)
		.bootstrapTable('getAllSelections')
    	.bootstrapTable('getData')  //所有data
    	.bootstrapTable('getData',"useCurrentPage",true); //当前页data
	});

    var $add = $('#insertOk');
    var $remove = $('#orgDel');

    $remove.click(function () {
        var ids = $.map($('#orgGrid').bootstrapTable('getSelections'), function (row) {
            return row.org_id;
        });
        if (ids.length != 1 ) {
            alert("请选择一行删除!");
            return;
        }
        if (confirm("你确定删除吗？")) {
            $.ajax({
                type: "post",
                url: "/org/remove",
                data: {"_method":"DELETE" ,
                        "org_id":ids[0]},
                dataType: 'JSON',
                success: function (data, status) {
                    if (data.success) {
                        $('#orgGrid').bootstrapTable('remove', {
                            field: 'org_id',
                            values: ids
                        });
                        successDialog('操作成功');
                    }else{
                        errorDialog('操作失败:'+data.msg);
                    }
                },
                error: function () {
                    errorDialog('操作失败:'+data.msg);
                },
                complete: function () {

                }
            });
        }
        else {
            warningDialog("点击了取消");
        }

    });

    $add.click(function () {
        $('#insertModal').modal("hide");
        var index = 0;//$('#orgGrid').bootstrapTable('getData').length;
        $.ajax({
            type: "put",
            url: "/org/add",
            data: $("#inserForm").serialize(),
            dataType: 'JSON',
            success: function (data, status) {
                if (data==null){
                    return ;
                }
                if (data.success) {
                    $('#orgGrid').bootstrapTable('insertRow', {
                        index: index,
                        row: {
                            org_id: $("#orgIdInsert").val(),
                            name: $("#orgNameInsert").val()
                        }
                    });
                    successDialog('操作成功');
                }else{
                    errorDialog('操作失败:'+data.msg);
                }

            },
            error: function () {
                errorDialog('操作失败');
            },
            complete: function () {

            }
        });

    });
	

	
});


function bindOrgsTable(){
	
	
	$('#orgGrid').bootstrapTable({
        url: '/org/orgs',         				//请求后台的URL（*）
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
        editable:true,//开启编辑模式
        // clickEdit: true,
        // onClickCell: function(field, value, row, $element) {
        //     $element.attr('contenteditable', true);
        //     $element.unbind("blur").blur(function() {
        //         var index = $element.parent().data('index');
        //         var tdValue = $element.html();
        //         alert(index);
        //         //saveData(index, field, tdValue);
        //     })
        // },
        height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "org_id",                     //每一行的唯一标识，一般为主键列
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
	        },
	        // , {
// 	            field: '',
// 	            title: '编号',
// 	            formatter:function(value, row, index){
// //	            	console.log(value);
// //	            	console.log(row);
// //	            	console.log(index);
// 	            	return '<a class="" data-toggle="modal" data-target="#showModal" data-unique-id="'+value+'" >'+value+'</a>';
// 	            }
            {
	            field: 'org_id',
	            title: '机构ID'

	        }, {
                field: 'name',
                title: '机构名',
				editable: {
					type: 'text',
					title: '机构名',
					validate: function (v) {
						if (!v) return '机构名不能为空';

					}
				}
            }

	        // , {
	        //     field: 'operate',
	        //     title: '操作',
	        //     align: 'center',
	        //     events: operateEvents,
	        //     formatter: operateFormatter
	        // }
        
        
        ],onLoadSuccess: function(dataSource){  //加载成功时执行
        	console.log(dataSource)
            console.info("加载成功");
        },
        onLoadError: function(){  //加载失败时执行
              console.info("加载数据失败");
        },
        onEditableSave: function (field, row, oldValue, $el) {
            $('#orgGrid').bootstrapTable("resetView");
            $.ajax({
                type: "post",
                url: "/org/edit",
                data: row,
                dataType: 'JSON',
                success: function (data, status) {
                    $('#orgGrid').bootstrapTable('refresh');
                    console.info(data);
                    if (data.success) {
                        successDialog('操作成功');
                    }else{
                        errorDialog('操作失败:'+data.msg);
                    }
                },
                error: function () {
                    $('#orgGrid').bootstrapTable('refresh');
                    errorDialog('编辑失败');
                },
                complete: function () {

                }

            });
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
				orgId: "required",
				orgName: {
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
				orgname1: {
					required: "Please enter a orgname",
					minlength: "Your orgname must consist of at least 2 characters"
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

function clearAdd(){
    $("#orgIdInsert").val("");
    $("#orgNameInsert").val("");
}