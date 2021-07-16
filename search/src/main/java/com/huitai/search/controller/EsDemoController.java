package com.huitai.search.controller;

import com.huitai.common.model.Result;
import com.huitai.core.base.BaseController;
import com.huitai.search.entity.EsDemo;
import com.huitai.search.service.EsDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: EsDemoController <br>
 * date: 2020/4/22 14:27 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
@RestController
public class EsDemoController extends BaseController {
    @Autowired
    private EsDemoService esDemoService;

    @GetMapping(value = "/createIndex")
    public Result createIndex(){
        esDemoService.addIndex(EsDemo.class);
        return Result.ok();
    }

    @GetMapping(value = "/delIndex")
    public Result delIndex(){
        esDemoService.deleteIndex(EsDemo.class);
        return Result.ok();
    }

    @GetMapping(value = "/test1")
    public Result test1(String id){
        esDemoService.save(new EsDemo(id,"测试1"+id));
        return Result.ok();
    }

    @GetMapping(value="test2")
    public Result test2(String id){
        EsDemo esDemo  = esDemoService.findById(id);
        return Result.ok(esDemo.getName());
    }

    @GetMapping(value = "/test3")
    public Result test3(){
        List<EsDemo> result = esDemoService.findAll();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", result);
        return Result.ok(map);
    }

    @GetMapping(value = "/test4")
    public Result test4(String id){
        esDemoService.deleteById(id);
        return Result.ok();
    }

    @GetMapping(value = "/test5")
    public Result test5(){
        esDemoService.deleteAll();
        return Result.ok();
    }

}
