package com.example.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ParamController {

    /**
     * PathVariable、RequestHeader、RequestParam、CookieValue
     */
    @GetMapping("/car/{id}/owner/{name}")
    @ResponseBody
    public Map getCar(@PathVariable("id") String id,
                      @PathVariable("name") String name,
                      @PathVariable Map<String, String> pv,
                      @RequestHeader("User-Agent") String ua,
                      @RequestHeader HttpHeaders header,
                      @RequestParam("inters") List<String> inters,
                      @RequestParam Map<String, String> params,
                      @CookieValue("Idea-c21deac9") String cookieValue,
                      @CookieValue("Idea-c21deac9") Cookie cookie) {
        // http://localhost:8080/car/1/owner/zs?inters=eat&inters=sleep
        HashMap<String, Object> map = new HashMap();
        map.put("id", id);
        map.put("name", name);
        map.put("pv", pv);
        map.put("ua", ua);
        map.put("header", header);
        map.put("inters", inters);
        map.put("params", params);
        map.put("cookieValue", cookieValue);
        System.out.println(cookie);
        return map;
    }


    /**
     * RequestBody
     */
    @PostMapping("/user")
    @ResponseBody
    public String save(@RequestBody String body) {
        return body;
    }


    @GetMapping("/goto")
    public String goToPage(HttpServletRequest request) {
        request.setAttribute("msg", "成功了");
        // 模拟跳转页面
        return "forward:/success";
    }

    /**
     * RequestAttribute
     */
    @GetMapping("/success")
    @ResponseBody
    public Map success(HttpServletRequest request,
                       @RequestAttribute("msg") String msg1) {
        Object msg2 = request.getAttribute("msg");
        HashMap<String, Object> map = new HashMap<>();
        map.put("byMethod", msg2);
        map.put("byAnno", msg1);
        return map;
    }


    /**
     * MatrixVariable
     * Spring Boot中默认去除了请求中分号之后的内容，造成无法使用矩阵变量。要开启矩阵变量需要自己添加一个urlPathHelper
     */
    @GetMapping("/cars/{path}")
    @ResponseBody
    public Map carsSell(@PathVariable("path") String path,
                        @MatrixVariable("low") String price,
                        @MatrixVariable("brand") String brand) {
        // http://localhost:8080/cars/sell;low=30;brand=byd,audi;
        HashMap<String, Object> map = new HashMap<>();
        map.put("price", price);
        map.put("brand", brand);
        map.put("path", path);
        return map;
    }

    /**
     * MatrixVariable
     * 请求中存在同名的矩阵变量，解析时须指定各自的路径变量
     */
    @GetMapping("/boss/{bossId}/{empId}")
    @ResponseBody
    public Map boss(@MatrixVariable(value = "age", pathVar = "bossId") Integer bossAge,
                    @MatrixVariable(value = "age", pathVar = "empId") Integer empAge) {
        // http://localhost:8080/boss/1;age=30/2;age=18
        HashMap<String, Object> map = new HashMap<>();
        map.put("bossAge", bossAge);
        map.put("empAge", empAge);
        return map;
    }
}
