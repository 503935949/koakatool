package com.supercode.koakatool.qywxworkflow.service.impl;

import com.supercode.TestBase;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class QxwxWorkflowServiceImplTest extends TestBase {

    @Autowired
    QxwxWorkflowServiceImpl qxwxWorkflowServiceImpl;

    @Test
    @Ignore
    public void getQywxIdentifier() {
        qxwxWorkflowServiceImpl.getQywxIdentifier();;
    }

    @Test
    @Ignore
    public void getQywxWorkInfoInfo() {
        qxwxWorkflowServiceImpl.getQywxWorkInfoInfo("1557905894","1768318210",0L);;
    }

    @Test
    @Ignore
    public void pullAllInfo() throws Exception {
        qxwxWorkflowServiceImpl.pullAllInfo("1557905894","1768318210");
        qxwxWorkflowServiceImpl.readToList();
    }

    @Test
    @Ignore
    public void readToList() throws Exception {
        qxwxWorkflowServiceImpl.pullAllInfo("1557905894","1768318210");
        qxwxWorkflowServiceImpl.readToList();
    }

    @Test
    public void textExcel() throws Exception {
        qxwxWorkflowServiceImpl.pullAllInfo("1557905894","1768318210");
        qxwxWorkflowServiceImpl.textExcel();
    }
}