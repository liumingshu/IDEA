//package com.wms.controller;
//
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//@Component
//public class VueStarter {
//
//    @PostConstruct
//    public void startVue() {
//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "npm", "run", "serve");
//            processBuilder.directory(new File("D:\\IDEA\\springboot_vue_wm\\wms-web"));
//            Process process = processBuilder.start();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}