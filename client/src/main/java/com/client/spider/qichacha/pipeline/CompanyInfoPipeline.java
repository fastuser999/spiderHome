package com.client.spider.qichacha.pipeline;

import com.client.spider.qichacha.dao.CompanyListQueneDAO;
import com.client.spider.qichacha.dao.CompanyInfoDAO;
import com.client.spider.qichacha.model.CompanyInfoRes;
import com.client.spider.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class CompanyInfoPipeline  implements Pipeline {


    public void process(ResultItems resultItems, Task task) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
        CompanyInfoDAO companyInfoDAO = sqlSession.getMapper(CompanyInfoDAO.class);
        CompanyInfoRes res = resultItems.get("res");
        if (null == res.getCompanyInfo()){
            CompanyListQueneDAO companyListQueneDAO = sqlSession.getMapper(CompanyListQueneDAO.class);
            companyListQueneDAO.updateCompanyQueneStatusFailureById(res.getLisId());
        }else {
            CompanyListQueneDAO companyListQueneDAO = sqlSession.getMapper(CompanyListQueneDAO.class);
            companyListQueneDAO.updateCompanyQueneById(res.getLisId());
            companyInfoDAO.addCompanyInfo(res.getCompanyInfo());
        }
        sqlSession.close();
    }
}
