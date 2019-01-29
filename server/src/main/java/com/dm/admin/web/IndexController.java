package com.dm.admin.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;


/**
 * Created by Lucare.Feng on 2017/2/28.
 */
@Controller
public class IndexController {

	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
    private Environment env;

	private int startCnt;

	@Value("${stepLength}")
	private int stepLength;
	
	private synchronized int getNextStart() {
		if (startCnt == 0) {
			startCnt = Integer.parseInt(env.getProperty("startCnt"));
		}
		logger.info("startCnt:" + startCnt);
		int current = startCnt;
		startCnt += stepLength;
		return current;
	}
	
	@RequestMapping(value = "/")
    public String index(Model model) {
        return "index";
    }
	
	@RequestMapping(value = "/range")
    @ResponseBody
    public JSONObject range(Model model) {
        JSONObject jsonObject = new JSONObject();
        String s = env.getProperty("stepLength");
        int start = getNextStart();
        jsonObject.accumulate("start", start);
        jsonObject.accumulate("end", start + stepLength);
        return jsonObject;
    }
}
