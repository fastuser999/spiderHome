package Storage;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.client.spider.util.DBHelper;
import com.client.spider.util.PropertiesReader;


public class MySQLStorage {
	private static Logger logger = LoggerFactory.getLogger(MySQLStorage.class);
	private static MySQLStorage instance = new MySQLStorage();
	private Set<String> companyUrls = (Set<String>)Collections.synchronizedSet(new HashSet<String>());
	private List<CompanyEntity> companyList = Collections.synchronizedList(new LinkedList<CompanyEntity>());
	private List<IdEntity> idList = Collections.synchronizedList(new LinkedList<IdEntity>());

	public MySQLStorage() {
		final int thCnt = 1;
		for(int i = 0; i < thCnt; ++i) {
			Thread thread = new Thread(new SaveTask(i));
			thread.start();
		}
	}
	
	public static MySQLStorage Instance() {
		return instance;
	}
	
	public void addCompany(List<CompanyEntity> list) {
		for (CompanyEntity entity : list) {
			if (!companyUrls.contains(entity.url)) {
				companyUrls.add(entity.url);
				companyList.add(entity);
			}
		}
	}
	
	public void initId(long id) {
		idList.add(new IdEntity(id, 0, null));
	}
	
	public void failedId(long id, int statusCode) {
		idList.add(new IdEntity(id, 2000 + statusCode, null));
	}
	
	public void failedId(long id, String msg) {
		idList.add(new IdEntity(id, -1, msg));
	}
	
	public void unknownId(long id, String msg) {
		idList.add(new IdEntity(id, -2, msg));
	}
	
	public void succeedId(long id) {
		idList.add(new IdEntity(id, 1, null));
	}
	
	public class IdEntity {
		public long id;
		public int status;
		public String message;
		public IdEntity(long id, int status, String message) {
			this.id = id;
			this.status = status;
			this.message = message;
		}
	}
	
	public class CompanyEntity {
		public String companyName;
		public String url;
		public long keyword;
		public int source;
		public String gs_status;
		public CompanyEntity(String companyName, String url, long keyword, int src, String gs_status) {
			this.companyName = companyName.replace('\'', '"');
			this.url = url;
			this.keyword = keyword;
			this.source = src;
			this.gs_status = gs_status;
		}
	}
	
	private Object lockObj = new Object();
    public class SaveTask implements Runnable {
    	final int batchSize = 50;
    	int tid;
    	public SaveTask(int tid) {
    		this.tid = tid;
    	}
    	private String getCompanySQL() {
    		StringBuilder sb = new StringBuilder();
    		sb.append("replace into company_raw(url, name, keyword, status, source, gs_status) values");
    		int cnt = 0;
    		
    		synchronized(lockObj) {
    			//logger.info("tid:{}, total:{}", tid, companyList.size());
    			while(companyList.size() > 0 && cnt < batchSize) {
        			CompanyEntity entity = companyList.remove(0);
        			cnt++;
        			if (cnt > 1) {
        				sb.append(",");
        			}
        			String sql = String.format("('%s','%s',%d,%d,%d,'%s')", entity.url, entity.companyName, entity.keyword, 0, entity.source, entity.gs_status);
        			sb.append(sql);
        		}
    			//logger.info("getCompanySQL cnt:{}, left:{}", cnt, companyList.size());
    		}
    		
    		if (cnt == 0) {
    			return null;
    		}
    		return sb.toString();
    	}
    	
    	private synchronized String getIdSQL() {
    		StringBuilder sb = new StringBuilder();
    		sb.append("replace into id_raw(id, status, message) values");
    		int cnt = 0;
    		
    		synchronized(lockObj) {
    			//logger.info("tid:{}, total:{}", tid, idList.size());
    			while(idList.size() > 0 && cnt < batchSize) {
        			IdEntity entity = idList.remove(0);
        			cnt++;
        			if (cnt > 1) {
        				sb.append(",");
        			}
        			String sql = String.format("(%d,%d,'%s')", entity.id, entity.status, entity.message);
        			sb.append(sql);
        		}
    			
    			//logger.info("getIdSQL cnt:{}, left:{}", cnt, idList.size());
    		}

    		if (cnt == 0) {
    			return null;
    		}
    		return sb.toString();
    	}
    	
        @Override
        public void run() {
        	String url = PropertiesReader.instance().getProperty("spring.datasource.url");
    		String name = PropertiesReader.instance().getProperty("spring.datasource.driverClassName");
    		String user = PropertiesReader.instance().getProperty("spring.datasource.username");
    		String password = PropertiesReader.instance().getProperty("spring.datasource.password");

        	DBHelper mysqlConn = null;
			try {
				mysqlConn = new DBHelper(url, name, user, password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
        	while(true) {
        		try {
        			String sql = this.getCompanySQL();
        			if (sql != null) {
        				logger.info("companyList left:{}", companyList.size());
        				mysqlConn.executeNonQuery(sql);
                	} else {
                		//logger.info("no company, sleep");
                		Thread.sleep(1000);
                	}
        			
        			sql = this.getIdSQL();
        			if (sql != null) {
        				logger.info("companyList left:{}", idList.size());
        				mysqlConn.executeNonQuery(sql);
                	} else {
                		//logger.info("no idList, sleep");
                		Thread.sleep(1000);
                	}
        		} catch (Exception ex) {
        			ex.printStackTrace();
        			logger.error(ex.getMessage());
        		}
        	}
        }
    }
}
