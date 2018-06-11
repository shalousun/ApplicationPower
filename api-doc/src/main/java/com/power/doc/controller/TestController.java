package com.power.doc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description:
 * 测试的Controller
 *
 * @author yu 2018/06/11.
 */
@Controller
@RequestMapping("student")
public class TestController {

    /**
     * 查询学生信息
     * @param name 学生姓名
     * @return
     */
    @PostMapping(value = "/getStudent/{name}")
    public Student getStudent(@PathVariable String name){
        System.out.println(name);
        return null;
    }
}
