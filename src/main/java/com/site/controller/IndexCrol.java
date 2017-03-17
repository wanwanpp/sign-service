package com.site.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 王萍 on 2017/3/15 0015.
 */

@Controller
public class IndexCrol {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/setpwd")
    public String setpwd() {
        return "pwd";
    }
}
