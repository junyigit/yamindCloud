package com.yamind.cloud.modules.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestController {

    @RequestMapping("/")
    public String index(){
        return "login";
    }

    @RequestMapping("/{path}.html")
    public String href(@PathVariable String path){
     // System.out.println("进来...");
        return path;
    }
}
