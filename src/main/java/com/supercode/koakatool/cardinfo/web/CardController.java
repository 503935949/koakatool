package com.supercode.koakatool.cardinfo.web;


import com.supercode.koakatool.cardinfo.service.ICardInfoService;
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
@RequestMapping("/cardinfo")
public class CardController {

	@Autowired
	private HttpSession session;

	@Autowired
	private ICardInfoService cardInfoServiceImpl;

	@Autowired
	private HttpServletResponse response;


//
//
//	@RequestMapping(value = "/getCardIdentifier")
//	@ResponseBody
//	public Response getCardIdentifier(){
//		String id = (String)session.getAttribute("Identifier");
//		String msg="";
//		if(null==id){
//			id = cardInfoServiceImpl.getCardIdentifier();
//			session.setAttribute("Identifier",id);
//		}else{
//			cardInfoServiceImpl.cardLogout(id);
//			id = cardInfoServiceImpl.getCardIdentifier();
//			session.setAttribute("Identifier",id);
//		}
//		Response response = new Response(id,"200",msg);
//		return response;
//	}
//
//
//	@RequestMapping(value = "/cardLogout")
//	@ResponseBody
//	public Response cardLogout(){
//		String id = (String)session.getAttribute("Identifier");
//		String msg="登出";
//		if(null!=id){
//			cardInfoServiceImpl.cardLogout(id);
//			session.setAttribute("Identifier",null);
//		}
//		Response response = new Response(id,"200",msg);
//		return response;
//	}
//
//
//	@RequestMapping(value = "/insertCordInfo",method = RequestMethod.POST )
//	@ResponseBody
//	public Response insertCordInfo(@RequestBody List<CardInfoDomain> cordInfos){
//		String msg="";
//		Response response = new Response(null,"200",msg);
//		try{
//			cardInfoServiceImpl.insertCordInfo(cordInfos);
//			msg="拉取成功";
//			LogUtils.info(msg+" \n\r");
//		}catch(Exception e){
//			msg="拉取失败";
//			response.setStat("500");
//			LogUtils.error(msg+" \n\r");
//		}
//		response.setMsg(msg);
//
//		try{
//			String id = (String)session.getAttribute("Identifier");
//			if(null!=id){
//				cardInfoServiceImpl.cardLogout(id);
//				session.setAttribute("Identifier",null);
//			}
//		}catch(Exception e){
//			LogUtils.error("拉取结束，关闭卡系统连接");
//		}
//		return response;
//	}


//	@RequestMapping(value = "/downLoadCardInfoXls")
//	@ResponseBody
//	public void downLoadFile4EtInfo03Or07(CardInfoQuery query) {
//		String strDate = DateUtil4Timeslecte.dateFormatString(new Date(), "yyyyMMddhhmmssSSS");
//		String filename ="门禁卡系统"+strDate+".xls";
//		Workbook wb = new HSSFWorkbook(); //创建一个空白的工作簿对象
//		Sheet st = wb.createSheet("考勤数据信息"); //基于上面的工作簿创建属于此工作簿的工作表名为"表格工作表第1页"
//		Row row = st.createRow(0); //创建属于上面工作表的第1行，参数从0开始可以是0～65535之间的任何一个
//		st.createFreezePane(0,1,0,1);
//		String[] names = {"编号","姓名","考勤类别","考勤日期","考勤时间","客户端IP"};
//		CellStyle ct = wb.createCellStyle();
//		ct.setFillBackgroundColor(HSSFColor.LIGHT_YELLOW.index);
//		//ct.setFillPattern(CellStyle.SOLID_FOREGROUND);
//		ct.setAlignment(CellStyle.ALIGN_CENTER);
//		ct.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		ct.setWrapText(true);
//		LogUtils.info("构架导出xls文件结构");
//		//名头
//		for(int i = 0;i < 6;i++){
//            st.setColumnWidth(i,20*256);
//
//			Cell cell = row.createCell(i);  //创建属于上面行的单元格，参数从0开始可以是0～255之间的任何一个，
//            cell.setCellStyle(ct);
//            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置此单元格的格式为文本，此句可以省略，WPS表格会自动识别。
//			cell.setCellValue(names[i]);   //直接在上面定义好的单元格输入内容("我是表格内容")，
//		}
//
//		//查询内容
//		LogUtils.info("查询打卡内容");
//		List<CardInfoDomain> list = cardInfoServiceImpl.getCardEvents(query);
//		if (null!=list&&list.size()>0){
//			LogUtils.info("查询打卡内容，完成，开始写入");
//			String infodata = "";
//			String infoName = "";
//			for(int i = 0;i < list.size();i++){
//				CardInfoDomain domain = list.get(i);
//				if(null==domain){
//					continue;
//				}
//                Row rowinfo = st.createRow(i+1);
//				for(int j = 0;j < 6;j++){
//					Cell cell = rowinfo.createCell(j);  //创建属于上面行的单元格，参数从0开始可以是0～255之间的任何一个，
//					//cell.setCellType(Cell.CELL_TYPE_STRING); //设置此单元格的格式为文本，此句可以省略，WPS表格会自动识别。
//					//直接在上面定义好的单元格输入内容("我是表格内容")，
//					String celldata =null;
//
//					switch(j){
//						case 0:
////							if(domain.getName().indexOf("MSA")>-1){
////								String[] strInfo = domain.getName().split(",|，");
////								celldata=strInfo[0];
////							}else{
////								celldata = domain.getName();
////							}
//							celldata = domain.getPersonalNr();
//							break;
//						case 1:
//							if(domain.getName().indexOf("MSA")>-1){
//								String[] strInfo = domain.getName().split(",|，");
//								if(strInfo.length>1){
//									celldata=strInfo[1];
//								}else{
//									celldata=strInfo[0];
//								}
//
//							}else{
//								celldata = domain.getName();
//							}
//							break;
//						case 2:
//							if(infodata.equals((domain.getDateStr()))
//								&&infoName.equals(domain.getPersonalNr())){
//								//第二次识别 下班 2
//								celldata="2";
//							}else{
//								//第二次识别 上班 1
//								celldata="1";
//							}
//
//							infodata = domain.getDateStr();
//							infoName = domain.getPersonalNr();
//							break;
//						case 3:celldata = domain.getDateStr();break;
//						case 4:
//						    if(null!=domain.getTimeStr())celldata = domain.getTimeStr().substring(0,5);break;
//						case 5:celldata = domain.getDeviceAddress();break;
//					}
//					cell.setCellValue(celldata);
//				}
//			}
//		}else{
//			LogUtils.info("查询打卡内容，完成,无信息");
//		}
//
////  			FileOutputStream ETFile;
////  			try {
////  			//创建一个文件输出流，指定到F盘根目录下的ETExample.et空白的表格文件
////				ETFile = new FileOutputStream("F:/ETExample.et");
////				//把工作表内容写到流里
////
////				ETFile.close(); //记得手动关闭流，POI不关闭用户打开的流。
////			} catch (FileNotFoundException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////  			catch (IOException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//
//
//		try {
//			// path是指欲下载的文件的路径。
//			//File file = new File(path);
//			// 取得文件名。
//			//String filename = file.getName();
//			// 以流的形式下载文件。
//			// InputStream fis = new BufferedInputStream(new FileInputStream(path));
//			//byte[] buffer = new byte[fis.available()];
//			//fis.read(buffer);
//			//fis.close();
//			// 清空response
//			response.reset();
//			// 设置response的Header
////              response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
//			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));//解决中文问题
//			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
//			response.setContentType("application/octet-stream");
//			//toClient.write(buffer);
//			wb.write(toClient);
//			toClient.flush();
//			toClient.close();
//			LogUtils.info("打卡记录导出成功");
//		} catch (IOException ex) {
//			ex.printStackTrace();
//			LogUtils.info("打卡记录导出失败");
//		}
//		// return response;
//	}


}
