package com.supercode.koakatool.faceinfo.web;


import com.supercode.koakatool.faceinfo.service.IFaceInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
*    
* 项目名称：
* 类名称：
* 类描述：   
* 创建人：林曌   
* 创建时间：
* 修改人：   
* 修改时间：
* 修改备注：   
* @version    
*    
*/
@Controller
@Api(hidden = true)
@RequestMapping("/faceinfo")
public class FaceInfoController {

	@Autowired
	private HttpSession session;

    @Autowired
    private IFaceInfoService faceInfoService;


	@Autowired
	private HttpServletResponse response;



//
//
//
//	@RequestMapping(value = "/insertFaceInfo",method = RequestMethod.POST )
//	@ResponseBody
//	public Response insertFaceInfo(@RequestBody List<FaceInfoDomain> datas){
//		String faceCookie = (String)session.getAttribute("faceCookie");
//
//        Response response = new Response();
//		String msg="";
//        try{
//            faceInfoService.insertFaceInfo(datas);
//            msg="JS测试数据人脸系统数据拉取成功";
//            LogUtils.info(msg+" \n\r");
//        }catch(Exception e){
//            msg="JS测试数据人脸系统数据拉取失败";
//            response.setStat("500");
//            LogUtils.error(msg+" \n\r");
//        }
//
//        response.setMsg(msg);
//		return response;
//	}
//
//
//    @RequestMapping(value = "/getFaceLogs")
//    @ResponseBody
//    public Response getFaceLogs(){
//        String msg="";
//        Response response = new Response();
//        try{
//            List<FaceInfoDomain> ls = faceInfoService.getFaceLogsInfo();
//            faceInfoService.insertFaceInfo(ls);
//            msg="人脸系统数据拉取成功";
//            LogUtils.info(msg+" \n\r");
//        }catch(Exception e){
//            msg="人脸系统数据拉取失败";
//            response.setStat("500");
//            LogUtils.error(msg+" \n\r");
//        }
//        return response;
//    }



}
