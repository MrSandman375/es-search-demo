package com.renjie.essearchdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @Auther: fan
 * @Date: 2020/12/11
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
public class Congyerenyuanxinxi {

    @Id
    private String xuhao;

    @Field(type = FieldType.Date,format = DateFormat.year_month_day)
    private Date jinsuoshijian;

    @Field(type = FieldType.Keyword)
    private String xingming;

    @Field(type = FieldType.Keyword)
    private String xingbie;

    @Field(type = FieldType.Keyword)
    private String shifouqianhetong;

    @Field(type = FieldType.Keyword)
    private String shifoudangyuan;

    @Field(type = FieldType.Keyword)
    private String shifoucanjiashebao;
}
