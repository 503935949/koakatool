package com.supercode.koakatool.platform.base.web;


/**
*    
* 项目名称：yueqingRMS   
* 类名称：BaseController   
* 类描述：   
* 创建人：林曌   
* 创建时间：2017年7月18日 下午2:15:59   
* 修改人：   
* 修改时间：2017年7月18日 下午2:15:59   
* 修改备注：   
* @version    
*    
*/
//@Controller
//@RequestMapping("")
//@ControllerAdvice()//全局异常捕获
public class BaseController {
	
	
//	 Logger logger =  Logger.getLogger(BaseController.class);
	/** 基于@ExceptionHandler异常处理 */  
//    @ExceptionHandler({ Error.class, Exception.class, Throwable.class })
//    public String exp(HttpServletRequest request, Exception ex) {  
//          
//        request.setAttribute("ex", ex);  
//          
//        
////        response.getWriter().write("sorry,Ajax请求出错！！！");
//        // 根据不同错误转向不同页面  
//        if(ex instanceof AppBusinessException) {  
//            return "error-business";  
//        }else if(ex instanceof AppSystemException) {  
//            return "error-parameter";  
//        } else {  
//            return "error";  
//        }  
//    }  
    
    
    /**
     * @Title: operateExp
     * @Description: 全局异常控制，记录日志
     *              任何一个方法发生异常，一定会被这个方法拦截到。然后，输出日志。封装Map并返回给页面显示错误信息： 
     *              特别注意：返回给页面错误信息只在开发时才使用，上线后，要把错误页面去掉，只打印log日志即可，防止信息外漏
     * @param: @param ex
     * @param: @param request
     * @return: String
     * @throws:
     */
//    @ExceptionHandler(RuntimeException.class)
//    public String operateExp(RuntimeException ex, HttpServletRequest request) {
//    logger.error(ex.getMessage(), ex);
//    logger.info("************* ------ 异常信息已记录（" + DateUtil.getNow("yyyy-MM-dd HH:mm:ss")+ "） ------- ***********");
//    request.setAttribute("errorTips", ex.getMessage());
//    request.setAttribute("ex", ex);
//    return "exception/error";
//    }
     
    /*
     * 记录Ajax异常日志，并将错误Ajax错误信息传递(回写)给前台展示,
     * 前台的jQuery的Ajax请求error中，可以打印错误提示信息   --  data.responseText   : 这里即是后台传递的错误提示
     * eg:
     * $.ajax({
                type : 'get',
                dataType : "json",
                url : ctx + '/test/test',
                accept:"application/json",
                success : function(data) {
                    console.log(data);
                },
                error : function(data, errorThrown) {
                    console.log(data);
                    alert("error" + data.responseText);
                }
            });
     */
//    @ExceptionHandler(Exception.class)
//    public void operateExpAjax(Exception ex, HttpServletResponse response) throws Exception {
//    logger.error(ex.getMessage(), ex);
//    logger.info("************* ------ 异常信息已记录（" + DateUtil.getNow("yyyy-MM-dd HH:mm:ss")+ "） ------- ***********");
//    //将Ajax异常信息回写到前台，用于页面的提示
//    response.getWriter().write("sorry,Ajax请求出错！！！");
//    }
     
     
    
}
