package com.hs.demo.controller;

import com.hs.demo.anno.SysLog;
import com.hs.demo.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;

    @GetMapping("/index")
    public void indexTest(){
        indexService.testPrint();
    }

    @GetMapping("/test")
    public String testLog(String name){
        return name;
    }

    @SysLog("测试")
    @GetMapping("/test2")
    public String testAnoLog(String name){
        return name;
    }

}
