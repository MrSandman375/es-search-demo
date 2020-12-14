package com.renjie.essearchdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//@SpringBootTest
class EsSearchDemoApplicationTests {


    @Test
    public void scheduledTask() throws ParseException {
//        File file = new File("C:/Users/Fan/Desktop/ik/ext_dict");
//        FileWriter fileWriter = new FileWriter(file, true);
//        PrintWriter printWriter = new PrintWriter(fileWriter);
//        List<String> names = Arrays.asList("红红","芳芳","黄旭东","毒奶色","夺冠","哈希");
//        for (String name : names) {
//            printWriter.println(name);
//            printWriter.flush();
//        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2006-08-28");
        System.out.println(date);
    }

}
