package com.renjie.essearchdemo.mapper;

import com.renjie.essearchdemo.entity.Suggest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Auther: fan
 * @Date: 2020/12/11
 * @Description:
 */
public interface SearchMapper extends ElasticsearchRepository<Suggest, String> {

//    @Query("{\"multi_match\": {\"query\": \"?0\",\"fields\": [\"shiwusuomingcheng\",\"bangongdizhi\",\"tongxundizhi\"]}}")
    @Query("{\n" +
            "    \"nested\": {\n" +
            "      \"path\": \"congyerenyuanxinxi\",\n" +
            "      \"query\": {\n" +
            "        \"match\": {\n" +
            "          \"congyerenyuanxinxi.xingming\": \"?0\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }")
    Page<Suggest> findSuggest(String keyword, Pageable pageable);
}
