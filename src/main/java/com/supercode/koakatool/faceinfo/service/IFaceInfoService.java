package com.supercode.koakatool.faceinfo.service;

import com.supercode.koakatool.faceinfo.domain.FaceInfoDomain;

import java.util.List;

public interface IFaceInfoService {

    void getFaceLogs();
    List<FaceInfoDomain> getFaceLogsInfo();
    void insertFaceInfo(List<FaceInfoDomain> datas);

    //public List<FaceInfoDomain> getFaceEvents(FaceInfoQuery query);

}
