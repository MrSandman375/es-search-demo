package com.renjie.essearchdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * @Auther: fan
 * @Date: 2020/12/11
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors
@Document(indexName = "mongo-firm")
public class Suggest {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String shiwusuomingcheng;

    @Field(type = FieldType.Keyword)
    private String bangongdizhi;

    @Field(type = FieldType.Keyword)
    private String tongxundizhi;

    @Field(type = FieldType.Nested)
    private List<Congyerenyuanxinxi> congyerenyuanxinxi;
}
