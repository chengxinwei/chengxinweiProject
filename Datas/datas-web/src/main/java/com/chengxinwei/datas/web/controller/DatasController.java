package com.chengxinwei.datas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xinwei.cheng on 2015/8/11.
 */
@Controller
@RequestMapping("/")
public class DatasController {


    @RequestMapping("/")
    public String index(){
        return "index";
    }


}
