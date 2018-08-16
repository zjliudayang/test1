package com.ly.test.controller;

import com.ly.test.model.User;
import com.ly.test.util.RedisUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration
public class TestController {
    private RedisUtil redisUtil = new RedisUtil();
    @RequestMapping(value="/test1",method= RequestMethod.POST)
    public String testGet(String id,String name)
    {
        System.out.println(id);
        System.out.println(name);
        return "hello";
    }

    @RequestMapping(value="/test2",method = RequestMethod.POST)
    public String test2(@RequestBody User user, @RequestParam String mark)
    {
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(mark);
        return "aaa";
    }

    @ResponseBody
    @RequestMapping(value = "/testRedis",method = RequestMethod.GET)
    public Object testRedis() {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        boolean result = redisUtil.lSet("1", list,10);
        if (!result) {
            return "failed";
        }
        return "successfully";
    }
    @ResponseBody
    @RequestMapping(value = "/getValueFromRedis",method = RequestMethod.GET)
    public Object getValueFromRedis(String key) {
        if (StringUtils.isEmpty(key)) {
            return "params error";
        }
        return redisUtil.lGet("1", 0, 100);
    }
    public static void main(String[] args) {
        List<String>list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        List<String>list2 = list.stream().filter(attr -> !"aaa".equals(attr)).collect(Collectors.toList());
        list2.forEach(name -> System.out.println(name));
        test2();
    }
    private static void test1()
    {
        new Thread(() -> System.out.println("aaa")).start();
    }
    private static void test2()
    {
        new Thread(() -> System.out.println(Thread.currentThread().getName() + ",bbb")).start();
    }
}
