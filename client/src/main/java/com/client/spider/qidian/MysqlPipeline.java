package com.client.spider.qidian;

import com.client.spider.qidian.dao.BookInfoDAO;
import com.client.spider.qidian.model.BookInfo;
import com.client.spider.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MysqlPipeline implements Pipeline {


    public void process(ResultItems resultItems, Task task) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
        BookInfoDAO bookInfoDAO = sqlSession.getMapper(BookInfoDAO.class);
        BookInfo info = resultItems.get("info");
        bookInfoDAO.insertBookInfo(info);
    }
}
