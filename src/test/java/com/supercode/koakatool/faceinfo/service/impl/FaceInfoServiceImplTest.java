package com.supercode.koakatool.faceinfo.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Component
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional()
public class FaceInfoServiceImplTest {

    @Autowired
    FaceInfoServiceImpl FaceInfoServiceImpl;
    @Test
    public void getFaceLogs() {
//        FaceInfoServiceImpl.getFaceLogs();;
    }
}