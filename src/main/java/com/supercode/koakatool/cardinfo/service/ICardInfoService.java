package com.supercode.koakatool.cardinfo.service;

import com.supercode.koakatool.cardinfo.domain.CardInfoDomain;
import com.supercode.koakatool.cardinfo.service.query.CardInfoQuery;

import java.text.ParseException;
import java.util.List;

public interface ICardInfoService {

    String getCardIdentifier();

    List<CardInfoDomain> getCardLogs(String identifier,String daystr,int skip);
    public List<CardInfoDomain> getAllCardLogs(String identifier) throws ParseException;
    void getCardLogs() throws ParseException;
    void cardLogout(String identifier);

    void insertCordInfo( List<CardInfoDomain> cordInfos);

    public List<CardInfoDomain> getCardEvents(CardInfoQuery query);

}
