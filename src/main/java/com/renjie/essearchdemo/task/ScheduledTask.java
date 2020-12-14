//package com.renjie.essearchdemo.task;
//
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @Auther: fan
// * @Date: 2020/12/10
// * @Description: 定时查询某个字段，把内容写入到扩展词典中（不知道够不够优雅，所以没实现）
// */
//@Component
//public class ScheduledTask {
//
//    @Autowired
//    private RestHighLevelClient client;
//
//    //定时查询并写入文件
//    @Scheduled
//    public void scheduledTask() throws IOException {
//        File file = new File("C:/Users/Fan/Desktop/ik/ext_dict");
//        FileWriter fileWriter = new FileWriter(file, true);
//        PrintWriter printWriter = new PrintWriter(fileWriter);
//
//
//
//        List<String> names = Arrays.asList("红红","芳芳","黄旭东","毒奶色","夺冠","哈希");
//        for (String name : names) {
//            printWriter.println(name);
//            printWriter.flush();
//        }
//    }
//
//}
