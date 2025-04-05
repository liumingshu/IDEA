package com.wms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//交互流程
//        客户端请求：客户端（如浏览器）发送HTTP请求到服务器。
//        Controller接收请求：Controller层接收到请求，解析请求参数，并调用Service层的方法。
//        Service处理业务逻辑：Service层处理业务逻辑，可能需要调用Mapper层的方法来获取或更新数据库中的数据。
//        Mapper执行数据库操作：Mapper层执行数据库的CRUD操作。
//        Service返回结果：Service层处理完业务逻辑后，将结果返回给Controller层。
//        Controller返回响应：Controller层将结果封装成HTTP响应，返回给客户端。
//
//
//        com.wms.controller
@SpringBootApplication
@MapperScan(basePackages = "com.wms.mapper")
public class WmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WmsApplication.class, args);
    }

}