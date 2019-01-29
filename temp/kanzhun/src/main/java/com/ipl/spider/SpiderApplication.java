package com.ipl.spider;

import com.ipl.spider.framework.processor.Processor;
import com.ipl.spider.framework.task.TaskStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan("com.ipl.spider.*")
@RestController
@RequestMapping("spider")
public class SpiderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpiderApplication.class, args);
        Processor.addTask("https://www.kanzhun.com", TaskStrategy.KANZHUN);
    }

    @GetMapping("start}")
    public String startUp() {

        return "ok";
    }

}
