package com.client.spider.qichacha.dao;

import com.client.spider.qichacha.model.CompanyInfo;

public interface CompanyInfoDAO {

    /**
     * 添加公司信息
     * @param info
     * @return
     */
    int addCompanyInfo(CompanyInfo info);
}
