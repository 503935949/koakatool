package com.supercode.koakatool.attendance.web;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supercode.koakatool.attendance.application.AttendanceAL;
import com.supercode.koakatool.attendance.application.OverSumAL;
import com.supercode.koakatool.attendance.application.PostponeTask;
import com.supercode.koakatool.attendance.domain.AttendanceDomain;
import com.supercode.koakatool.attendance.domain.OvertimeDomain;
import com.supercode.koakatool.attendance.service.IAttendanceService;
import com.supercode.koakatool.attendance.service.query.AttendanceQuery;
import com.supercode.koakatool.cardinfo.service.ICardInfoService;
import com.supercode.koakatool.faceinfo.service.IFaceInfoService;
import com.supercode.koakatool.qywxworkflow.domain.SPExcelEntity;
import com.supercode.koakatool.qywxworkflow.service.impl.QxwxWorkflowServiceImpl;
import com.supercode.koakatool.system.DateUtil4Timeslecte;
import com.supercode.koakatool.system.EasyExcelUtils;
import com.supercode.koakatool.system.LogUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/attendance")
@Api(tags = "attendance",description = "打卡日志",produces="application/json",hidden=true)
public class AttendanceController {

    @Autowired
    private HttpSession session;

    @Autowired
    private IFaceInfoService faceInfoService;

    @Autowired
    private QxwxWorkflowServiceImpl qxwxWorkflowServiceImpl;

    @Autowired
    private ICardInfoService cardInfoServiceImpl;


    @Autowired
    private HttpServletResponse response;

    @Autowired
    private IAttendanceService service;

    @Autowired
    private AttendanceAL al;

    @Autowired
    private OverSumAL overSumAL;

    public static final int TIMES = 2;

    /**
     * @return DEMO_LIST 界面
     */
    @RequestMapping(value = "/")
    public String userPage(){
        return "pages/attendance/attendanceIndex";
    }



    @RequestMapping(value = "/getKoaKaInfo")
    @ResponseBody
    public void getKoaKaInfo(){

        try{
            service.getKoaKaInfo();
        }catch(Exception e){
            LogUtils.error("尝试获取最新信息：失败");
            throw e;
        }

    }

    /**
     *
     * @param query
     * @return
     */
    @ApiOperation(value="获取员工打卡记录", notes="分页查询",produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "job_number", value = "工号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "姓名", required = false, dataType = "String")

    })
    @RequestMapping(value = "/getAttendanceEvents")
    @ResponseBody
    public PageInfo<AttendanceDomain> getAttendanceEvents(AttendanceQuery query){
        PageHelper.startPage(query.getPageNum(), query.getPageSize());

        List<AttendanceDomain> list = service.getAttendanceEvents(query);
        PageInfo<AttendanceDomain> pi = new PageInfo<>(list);
        return pi;
    }

    @RequestMapping(value = "/downLoadKoakaInfoXls")
    @ResponseBody
    public void downLoadFile4EtInfo03Or07(AttendanceQuery query) {
//        try{
//            getKoaKaInfo();
//        }catch(Exception e){
//            LogUtils.error("尝试获取最新信息：失败");
//
//        }
        String strDate = DateUtil4Timeslecte.dateFormatString(new Date(), "yyyyMMddhhmmssSSS");
        String filename ="AT"+strDate+".xls";
        Workbook wb = new HSSFWorkbook(); //创建一个空白的工作簿对象
        Sheet st = wb.createSheet("考勤数据信息"); //基于上面的工作簿创建属于此工作簿的工作表名为"表格工作表第1页"
        Row row = st.createRow(0); //创建属于上面工作表的第1行，参数从0开始可以是0～65535之间的任何一个
        st.createFreezePane(0,1,0,1);
        String[] names = {"编号","姓名","考勤类别","考勤日期","考勤时间","客户端IP"};
        CellStyle ct = wb.createCellStyle();
        ct.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());//HSSFColor.LIGHT_YELLOW.index
        //ct.setFillPattern(CellStyle.SOLID_FOREGROUND);
        ct.setAlignment(HorizontalAlignment.CENTER);//HorizontalAlignment.CENTER
        ct.setVerticalAlignment(VerticalAlignment.CENTER);//VerticalAlignment.CENTER
        ct.setWrapText(true);

        CellStyle ctw = wb.createCellStyle();
        ctw.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());//HSSFColor.LIGHT_YELLOW.index
        //ct.setFillPattern(CellStyle.SOLID_FOREGROUND);
        ctw.setAlignment(HorizontalAlignment.CENTER);//HorizontalAlignment.CENTER
        ctw.setVerticalAlignment(VerticalAlignment.CENTER);//VerticalAlignment.CENTER
        ctw.setWrapText(true);
        Font fontw = wb.createFont();
        //font.setFontHeightInPoints((short) 30);
        fontw.setItalic(true);
        fontw.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());//HSSFColor.DARK_BLUE.index
        ctw.setFont(fontw);

        CellStyle cte = wb.createCellStyle();
        cte.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());//HSSFColor.LIGHT_YELLOW.index
        //ct.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cte.setAlignment(HorizontalAlignment.CENTER);
        cte.setVerticalAlignment(VerticalAlignment.CENTER);
        cte.setWrapText(true);
        Font fonte = wb.createFont();
        //font.setFontHeightInPoints((short) 30);
        fonte.setItalic(true);
        fonte.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());//HSSFColor.RED.index
        cte.setFont(fonte);



        LogUtils.info("构架导出xls文件结构");
        //名头
        for(int i = 0;i < 6;i++){
            st.setColumnWidth(i,20*256);

            Cell cell = row.createCell(i);  //创建属于上面行的单元格，参数从0开始可以是0～255之间的任何一个，
            cell.setCellStyle(ct);
            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置此单元格的格式为文本，此句可以省略，WPS表格会自动识别。
            cell.setCellValue(names[i]);   //直接在上面定义好的单元格输入内容("我是表格内容")，
        }

        //查询内容
        LogUtils.info("查询打卡内容");
//        List<AttendanceDomain> a = service.getFaceCardEvents(query);
//        service.insert(a);
        try {
            al.getPostponeInfosByTask(query);
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtils.error(e.toString());
        }
        List<AttendanceDomain> list = service.getPostponeEvents(query);

        //key   年月_姓名
        Map<String,Integer> changeMap = new HashMap<>();

        if (null!=list&&list.size()>0){
            LogUtils.info("查询打卡内容，完成，开始写入");
            String infodata = "";
            String infoName = "";
            for(int i = 0;i < list.size();i++){
                AttendanceDomain domain = list.get(i);
                if(null==domain){
                    continue;
                }
                Row rowinfo = st.createRow(i+1);
                String type;
                if(infodata.equals((domain.getDateStr()))
                        &&infoName.equals(domain.getPersonalNr())){
                    //第二次识别 下班 2
                    type="2";
                }else{
                    //第二次识别 上班 1
                    type="1";
                }
                String overType;
                for(int j = 0;j < 6;j++){
                    Cell cell = rowinfo.createCell(j);  //创建属于上面行的单元格，参数从0开始可以是0～255之间的任何一个，
                    //cell.setCellType(Cell.CELL_TYPE_STRING); //设置此单元格的格式为文本，此句可以省略，WPS表格会自动识别。
                    //直接在上面定义好的单元格输入内容("我是表格内容")，
                    String celldata =null;

                    switch(j){
                        case 0:
//							if(domain.getName().indexOf("MSA")>-1){
//								String[] strInfo = domain.getName().split(",|，");
//								celldata=strInfo[0];
//							}else{
//								celldata = domain.getName();
//							}
                            celldata = domain.getPersonalNr();
                            break;
                        case 1:
//                            if(domain.getName().indexOf("MSA")>-1){
//                                String[] strInfo = domain.getName().split(",|，");
//                                if(strInfo.length>1){
//                                    celldata=strInfo[1];
//                                }else{
//                                    celldata=strInfo[0];
//                                }
//
//                            }else{
//                                celldata = domain.getName();
//                            }
                            celldata = domain.getUsername();
                            break;
                        case 2:
//                            if(infodata.equals((domain.getDateStr()))
//                                    &&infoName.equals(domain.getPersonalNr())){
//                                //第二次识别 下班 2
//                                celldata="2";
//                            }else{
//                                //第二次识别 上班 1
//                                celldata="1";
//                            }
                            celldata=type;
                            infodata = domain.getDateStr();
                            infoName = domain.getPersonalNr();
                            break;
                        case 3:celldata = domain.getDateStr();break;
                        case 4:
                            if(null!=domain.getTimeStr()){
                                celldata = domain.getTimeStr().substring(0,5);
                                if("1".equals(type)){
                                    String[] res = postponeoverdue(celldata,domain.getTimestamp(),domain.getDateStr().substring(0,7)+"_"+domain.getPersonalNr(),changeMap);
                                    celldata = res[0];
                                    if("2".equals(res[1])){
                                        cell.setCellStyle(ctw);
                                    }else if("3".equals(res[1])){
                                        cell.setCellStyle(cte);
                                    }

                                }
                            }
                            break;
                        case 5:
                            celldata = "";
                            if(domain.getLogIdInternal().indexOf("C")>-1){
                                celldata = "门卡系统";
                            }else if(domain.getLogIdInternal().indexOf("F")>-1){

                                celldata = "人脸系统";
                            }
                            break;
                    }
                    cell.setCellValue(celldata);
                }
            }
        }else{
            LogUtils.info("查询打卡内容，完成,无信息");
        }
        OutputStream toClient = null;
        try {
            // path是指欲下载的文件的路径。
            //File file = new File(path);
            // 取得文件名。
            //String filename = file.getName();
            // 以流的形式下载文件。
            // InputStream fis = new BufferedInputStream(new FileInputStream(path));
            //byte[] buffer = new byte[fis.available()];
            //fis.read(buffer);
            //fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
//              response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));//解决中文问题
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            //toClient.write(buffer);
            wb.write(toClient);
            toClient.flush();
            toClient.close();
            LogUtils.info("打卡记录导出成功");
        } catch (IOException ex) {
            ex.printStackTrace();
            LogUtils.info("打卡记录导出失败");
        }finally {
            try {
                toClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // return response;
    }






    @RequestMapping(value = "/getOverSumInfosEtInfo")
    @ResponseBody
    public void getOverSumInfosEtInfo(AttendanceQuery query) {

        String strDate = DateUtil4Timeslecte.dateFormatString(new Date(), "yyyyMMddhhmmssSSS");
        String filename ="OT"+ strDate+".xls";
        Workbook wb = new HSSFWorkbook(); //创建一个空白的工作簿对象
        Sheet st = wb.createSheet("加班排名统计"); //基于上面的工作簿创建属于此工作簿的工作表名为"表格工作表第1页"
        Row row = st.createRow(0); //创建属于上面工作表的第1行，参数从0开始可以是0～65535之间的任何一个
        st.createFreezePane(0,1,0,1);
        String[] names = {"排名","编号","姓名","加班时长（小时）"};
        CellStyle ct = wb.createCellStyle();
        ct.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());//HSSFColor.LIGHT_YELLOW.index
        //ct.setFillPattern(CellStyle.SOLID_FOREGROUND);
        ct.setAlignment(HorizontalAlignment.CENTER);
        ct.setVerticalAlignment(VerticalAlignment.CENTER);
        ct.setWrapText(true);
        LogUtils.info("构架导出xls文件结构");
        //名头
        for(int i = 0;i < 4;i++){
            st.setColumnWidth(i,20*256);

            Cell cell = row.createCell(i);  //创建属于上面行的单元格，参数从0开始可以是0～255之间的任何一个，
            cell.setCellStyle(ct);
            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置此单元格的格式为文本，此句可以省略，WPS表格会自动识别。
            cell.setCellValue(names[i]);   //直接在上面定义好的单元格输入内容("我是表格内容")，
        }

        //查询内容
        LogUtils.info("查询打卡内容");

        try {
            overSumAL.getOverSumInfos(query);
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtils.error(e.toString());
        }
        List<OvertimeDomain> list = overSumAL.getOvertimeEvents(query);
        if (null!=list&&list.size()>0){
            LogUtils.info("查询加班内容，完成，开始写入");
            for(int i = 0;i < list.size();i++){
                OvertimeDomain domain = list.get(i);
                if(null==domain){
                    continue;
                }
                Row rowinfo = st.createRow(i+1);
                for(int j = 0;j < 6;j++){
                    Cell cell = rowinfo.createCell(j);  //创建属于上面行的单元格，参数从0开始可以是0～255之间的任何一个，
                    //cell.setCellType(Cell.CELL_TYPE_STRING); //设置此单元格的格式为文本，此句可以省略，WPS表格会自动识别。
                    //直接在上面定义好的单元格输入内容("我是表格内容")，
                    String celldata =null;

                    switch(j){
                        case 0:
                            celldata = domain.getRownum();
                            break;
                        case 1:
                            celldata = domain.getPersonalNr();
                            break;
                        case 2:
                            celldata = domain.getUsername();
                            break;
                        case 3:
                            celldata = domain.getOvertimeh();
                            break;

                    }
                    cell.setCellValue(celldata);
                }
            }
        }else{
            LogUtils.info("查询加班统计内容，完成,无信息");
        }
        OutputStream toClient = null;
        try {

            response.reset();
            // 设置response的Header
//              response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));//解决中文问题
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            //toClient.write(buffer);
            wb.write(toClient);
            toClient.flush();
            toClient.close();
            LogUtils.info("加班排名导出成功");
        } catch (IOException ex) {
            ex.printStackTrace();
            LogUtils.info("加班排名导出失败");
        }finally {
            try {
                toClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // return response;
    }




    @RequestMapping(value = "/getOverNightInfosEtInfo")
    @ResponseBody
    public void getOverNightInfosEtInfo(AttendanceQuery query) {

        String strDate = DateUtil4Timeslecte.dateFormatString(new Date(), "yyyyMMddhhmmssSSS");
        String filename ="ON"+ strDate+".xls";
        Workbook wb = new HSSFWorkbook(); //创建一个空白的工作簿对象
        Sheet st = wb.createSheet("加班排名统计"); //基于上面的工作簿创建属于此工作簿的工作表名为"表格工作表第1页"
        Row row = st.createRow(0); //创建属于上面工作表的第1行，参数从0开始可以是0～65535之间的任何一个
        st.createFreezePane(0,1,0,1);
        String[] names = {"排名","编号","姓名","熬夜天数","详情"};
        CellStyle ct = wb.createCellStyle();
        ct.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());//HSSFColor.LIGHT_YELLOW.index
        //ct.setFillPattern(CellStyle.SOLID_FOREGROUND);
        ct.setAlignment(HorizontalAlignment.CENTER);
        ct.setVerticalAlignment(VerticalAlignment.CENTER);
        ct.setWrapText(true);
        LogUtils.info("构架导出xls文件结构");
        //名头
        for(int i = 0;i < 4;i++){
            st.setColumnWidth(i,20*256);
            Cell cell = row.createCell(i);  //创建属于上面行的单元格，参数从0开始可以是0～255之间的任何一个，
            cell.setCellStyle(ct);
            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置此单元格的格式为文本，此句可以省略，WPS表格会自动识别。
            cell.setCellValue(names[i]);   //直接在上面定义好的单元格输入内容("我是表格内容")，
        }

        //查询内容
        LogUtils.info("查询打卡内容");


        List<OvertimeDomain> list = overSumAL.getOverNightInfos(query);
        if (null!=list&&list.size()>0){
            LogUtils.info("查询加班内容，完成，开始写入");
            for(int i = 0;i < list.size();i++){
                OvertimeDomain domain = list.get(i);
                if(null==domain){
                    continue;
                }
                Row rowinfo = st.createRow(i+1);
                for(int j = 0;j < 6;j++){
                    Cell cell = rowinfo.createCell(j);  //创建属于上面行的单元格，参数从0开始可以是0～255之间的任何一个，
                    //cell.setCellType(Cell.CELL_TYPE_STRING); //设置此单元格的格式为文本，此句可以省略，WPS表格会自动识别。
                    //直接在上面定义好的单元格输入内容("我是表格内容")，
                    String celldata =null;

                    switch(j){
                        case 0:
                            celldata = domain.getRownum();
                            break;
                        case 1:
                            celldata = domain.getPersonalNr();
                            break;
                        case 2:
                            celldata = domain.getUsername();
                            break;
                        case 3:
                            celldata = domain.getDaysum();
                            break;
                        case 4:
                            celldata = domain.getOvertimeh();
                            break;

                    }
                    cell.setCellValue(celldata);
                }
            }
        }else{
            LogUtils.info("查询加班统计内容，完成,无信息");
        }
        OutputStream toClient = null;
        try {

            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));//解决中文问题
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            wb.write(toClient);
            toClient.flush();
            toClient.close();
            LogUtils.info("熬夜加班排名导出成功");
        } catch (IOException ex) {
            ex.printStackTrace();
            LogUtils.info("熬夜加班排名导出失败");
        }finally {
            try {
                toClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // return response;
    }

    public String[] postponeoverdue(String timestr,String timestamp,String key,Map map){
        //判断是否迟到
        boolean usOvertime=false;
        boolean usOvertime2=false;
        String[] response ={timestr,"1"};
        try {
            usOvertime  = DateUtil4Timeslecte.DateCompare(timestr+":00",
                    AttendanceAL.DEFAULT_START, AttendanceAL.DEFAULT_FORTIME) == 1;
            usOvertime2  = DateUtil4Timeslecte.DateCompare(timestr+":00",
                    "08:45:00", AttendanceAL.DEFAULT_FORTIME) == 1;
        } catch (ParseException e) {
            e.printStackTrace();
            return response;
        }
        if((!usOvertime2)&&usOvertime) {

            if (map.containsKey(key)) {
                Integer i = (Integer) map.get(key);
                if (i == TIMES) {
                    String[] response3 ={timestr,"3"};
                    return response3;
                }
                map.put(key,i+1);
            }else{
                map.put(key,1);
            }
            Date timeNumDate = new Date(Long.parseLong(timestamp)*1000-900000 );
            String newstr = (PostponeTask.DEF_FTIME.format(timeNumDate)).substring(0,5);
            String[] response2 ={newstr,"2"};
            return response2 ;
        }else {
            return response;
        }
    }


    @RequestMapping(value = "/getQxwxWorkflowEtInfo")
    public void textExcel(HttpServletResponse response,AttendanceQuery query) throws Exception  {

        Map m = new HashMap();
        String strDate = DateUtil4Timeslecte.dateFormatString(new Date(), "yyyyMMddhhmmssSSS");
        String filename ="SP"+ strDate;

        try {
            String s = DateUtil4Timeslecte.dateTimestamp(query.getStartTime(),AttendanceAL.DEFAULT_FORMAT)
                    .toString().substring(0,10);
            String e = DateUtil4Timeslecte.dateTimestamp(query.getEndTime(),AttendanceAL.DEFAULT_FORMAT)
                    .toString().substring(0,10);
            qxwxWorkflowServiceImpl.pullAllInfo(s,e);
            m.put("审批导出",qxwxWorkflowServiceImpl.readToList());
            EasyExcelUtils.createExcelStreamMutilByEaysExcel(response,m,ExcelTypeEnum.XLSX,filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
