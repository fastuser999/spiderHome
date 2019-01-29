package com.client.spider.qichacha.dao;

import com.client.spider.qichacha.model.CompanyQuene;

import java.util.List;

public interface CompanyListQueneDAO {
    int addFinalQuene(CompanyQuene quene);

    List<CompanyQuene> selectUndoList(CompanyQuene quene);

    int updateCompanyQueneById(int id);
    int updateCompanyQueneStatusFailureById(int id);
}
