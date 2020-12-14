package com.renjie.essearchdemo.service.impl;

import com.renjie.essearchdemo.entity.Suggest;
import com.renjie.essearchdemo.mapper.SearchMapper;
import com.renjie.essearchdemo.service.SearchService;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: fan
 * @Date: 2020/12/9
 * @Description:
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private SearchMapper suggestMapper;

    private Pageable pageable = PageRequest.of(0,10);

    //需要查询的索引
    private static final String INDEX = "mongo-firm";

    //嵌套查询示例
    @Override
    public Object search(String keyword, Integer from, Integer size) throws IOException {
        //先创建一个请求,把查询的索引放到里面
        SearchRequest searchRequest = new SearchRequest(INDEX);
        //构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //开始添加条件(这里演示一个嵌套查询)
        /**
         * GET mongo-firm/_search
         * {
         *   "_source": ["congyerenyuanxinxi.xingming","congyerenyuanxinxi.xingbie","congyerenyuanxinxi.shifoudangyuan","congyerenyuanxinxi.jinsuoshijian"],
         *   "query": {
         *     "bool": {
         *       "must": [
         *          {
         *             "nested": {
         *               "path": "congyerenyuanxinxi",
         *               "query": {
         *                 "bool": {
         *                   "must": [
         *                     {"match": {"congyerenyuanxinxi.xingming": "边晓红"}},
         *                     {"match": {"congyerenyuanxinxi.xingbie": "女"}},
         *                     {"match": {"congyerenyuanxinxi.shifoudangyuan": "否"}},
         *                     {
         *                       "range": {
         *                       "congyerenyuanxinxi.jinsuoshijian": {
         *                        "gte": "2006-08-28",
         *                        "lt":  "2006-09-02",
         *                        "format": "yyyy-MM-dd"
         *                        }
         *                       }
         *                     }
         *                   ]
         *                 }
         *               }
         *             }
         *           }
         *         ]
         *     }
         *   }
         * }
         */
        //构建几个match查询
        MatchQueryBuilder xingming = QueryBuilders.matchQuery("congyerenyuanxinxi.xingming", "边晓红");
        MatchQueryBuilder xingbie  = QueryBuilders.matchQuery("congyerenyuanxinxi.xingbie","女");
        MatchQueryBuilder shifoudangyuan = QueryBuilders.matchQuery("congyerenyuanxinxi.shifoudangyuan","否");
        //构建range范围查询
        String fromDate = "2006-02-28";
        String toDate = "2006-09-02";
        QueryBuilder jinsuoshijian = QueryBuilders.rangeQuery("congyerenyuanxinxi.jinsuoshijian").from(fromDate).to(toDate).format("yyyy-MM-dd");

        //把match查询放到bool查询中
        QueryBuilder must = QueryBuilders.boolQuery()
                .must(xingming)
                .must(xingbie)
                .must(shifoudangyuan)
                .must(jinsuoshijian);

        //把bool查询放到嵌套查询中
        QueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery("congyerenyuanxinxi",must, ScoreMode.None);

        //把嵌套查询放到放到bool查询中
        QueryBuilder  boolQueryBuilder = QueryBuilders.boolQuery().must(nestedQueryBuilder);

        //查询结果高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("congyerenyuanxinxi.xingming").preTags("<span style='color:red'>").postTags("</span>").requireFieldMatch(false);

        //把整个查询条件放到query中
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        searchSourceBuilder.highlighter(highlightBuilder);
        //只返回包含的字段
        String[] includeFields = new String[]{"congyerenyuanxinxi.xingming","congyerenyuanxinxi.xingbie","congyerenyuanxinxi.shifoudangyuan","congyerenyuanxinxi.jinsuoshijian"};
        String[] excludesFields = new String[]{};
        searchSourceBuilder.fetchSource(includeFields,excludesFields);
        //把条件放到请求中
        searchRequest.source(searchSourceBuilder);
        //执行请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //处理高亮
        for (SearchHit docs : searchResponse.getHits().getHits() ) {
            Map<String, HighlightField> highlightFields = docs.getHighlightFields();
            HighlightField highlightField = highlightFields.get("congyerenyuanxinxi.xingming");
            Text[] fragments = highlightField.fragments();
            String h = "";
            for (Text text : fragments){
                h += text;
            }
            Map<String, Object> sourceAsMap = docs.getSourceAsMap();
            sourceAsMap.put("congyerenyuanxinxi.xingming", h);
        }
        return searchResponse;
    }

    //搜索提示
    @Override
    public Object searchText(String text) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(text,"shiwusuomingcheng","bangongdizhi","tongxundizhi","pizhunshelijiguan");

        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        HighlightBuilder.Field shiwusuomingcheng = new HighlightBuilder.Field("shiwusuomingcheng");
//        HighlightBuilder.Field bangongdizhi = new HighlightBuilder.Field("bangongdizhi");
//        HighlightBuilder.Field tongxundizhi = new HighlightBuilder.Field("tongxundizhi");
//        HighlightBuilder.Field pizhunshelijiguan = new HighlightBuilder.Field("pizhunshelijiguan");
//        List<HighlightBuilder.Field> fields = new ArrayList<>();
//        fields.add(shiwusuomingcheng);
//        fields.add(bangongdizhi);
//        fields.add(tongxundizhi);
//        fields.add(pizhunshelijiguan);
//        highlightBuilder.fields(fields).preTags("<span style='color:red'>").postTags("</span>");

//        highlightBuilder.field("shiwusuomingcheng").preTags("<span style='color:red'>").postTags("</span>");
//        highlightBuilder.field("bangongdizhi").preTags("<span style='color:red'>").postTags("</span>");
//        highlightBuilder.field("tongxundizhi").preTags("<span style='color:red'>").postTags("</span>");
//        highlightBuilder.field("pizhunshelijiguan").preTags("<span style='color:red'>").postTags("</span>");

        sourceBuilder.query(queryBuilder);
        sourceBuilder.fetchSource("shiwusuomingcheng"," ");
        sourceBuilder.from(0).size(9);

        searchRequest.source(sourceBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        for (SearchHit searchHit : response.getHits().getHits()){
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            HighlightField shiwusuomingcheng = highlightFields.get("shiwusuomingcheng");
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();

            Text[] texts = shiwusuomingcheng.fragments();
            String s = "";
            for (Text text1 : texts){
                s += text1;
            }
            sourceAsMap.put("shiwusuomingcheng",s);
        }
        SearchHit[] hits = response.getHits().getHits();
        List<String> stringList = new ArrayList<>();
        for (SearchHit hit : hits){
            String shiwusuomingcheng = (String) hit.getSourceAsMap().get("shiwusuomingcheng");
            stringList.add(shiwusuomingcheng);
        }

        return stringList;
    }

    @Override
    public Page<Suggest> getSuggest(String keyword) {
        return suggestMapper.findSuggest(keyword, pageable);
    }







}
