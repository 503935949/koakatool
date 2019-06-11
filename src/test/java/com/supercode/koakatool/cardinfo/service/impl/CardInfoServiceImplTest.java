package com.supercode.koakatool.cardinfo.service.impl;

import com.supercode.koakatool.faceinfo.service.impl.FaceInfoServiceImpl;
import com.supercode.koakatool.system.DateUtil4Timeslecte;
import com.supercode.koakatool.system.LogUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@Component
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional()
public class CardInfoServiceImplTest {

    @Autowired
    CardInfoServiceImpl CardInfoServiceImpl;

    @Test
    public void main() throws ParseException {
        //登录获取令牌
        String identifier =  CardInfoServiceImpl.getCardIdentifier();
        // String identifier="TXlBcGlLZXk6ODAzMTVjNzQtNWFkYS00OGE0LWJlMWMtMTRmY2RkZjVhZWU5";
        //拉取门卡信息
        List m = null;
        try{
            m =  CardInfoServiceImpl.getAllCardLogs(identifier);
            LogUtils.info(m+" \n\r");
        }catch(Exception e){
            CardInfoServiceImpl.cardLogout(identifier);
        }
        CardInfoServiceImpl.cardLogout(identifier);
//        if(m!=null&&m.size()>0){
//            CardInfoServiceImpl.insertCordInfo(m);
//        }




    }
}