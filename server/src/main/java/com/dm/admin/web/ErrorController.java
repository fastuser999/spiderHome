package com.dm.admin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import com.dm.common.util.AjaxUtils;
 
@ControllerAdvice
public class ErrorController {
    private Logger logger = LoggerFactory.getLogger(ErrorController.class);
 
    /**
     * 系统异常处理，比如：404,500
     * @param req
     * @param resp
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JSONObject defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        return AjaxUtils.getErrorObject("服务器繁忙，请稍后再试！");
    }
    
}