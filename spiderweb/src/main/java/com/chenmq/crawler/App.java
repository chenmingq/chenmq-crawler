package com.chenmq.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler
 * @Description: TODO
 * @date 2018-03-29 下午10:52
 */
@Controller
@ComponentScan
@EnableAutoConfiguration
public class App extends WebMvcConfigurationSupport implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }

    public static void main(String[] args)  {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        LOG.info("启动完成[{}]",System.currentTimeMillis());
    }
}
