package Storage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.client.spider.util.PropertiesReader;

import redis.clients.jedis.Jedis;

public class RedisStorage {
	private ExecutorService executorService = Executors.newSingleThreadExecutor();
	Jedis jedis = null;
	
	public static void addCompany(String companyName, String url) {
		//
	}
	
    public class RedisTask implements Runnable {
        String key;
        String value;
        int index;

        private Jedis getJedis() {
            if (jedis == null) {
                String ip = PropertiesReader.instance().getProperty("redis.ip");
                String pass = PropertiesReader.instance().getProperty("redis.pass");
                jedis = new Jedis(ip, 6379);
                jedis.auth(pass);
            }
            return jedis;
        }

        public RedisTask(String key, String value, int index) {
            this.key = key;
            this.value = value;
            this.index = index;
        }

        @Override
        public void run() {
            getJedis().select(index);
            getJedis().set(key, value);
        }
    }
}
