package com.renjie.essearchdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.renjie.essearchdemo.domain.Response;
import com.renjie.essearchdemo.entity.Suggest;
import com.renjie.essearchdemo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @Auther: fan
 * @Date: 2020/12/9
 * @Description:
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/demo")
    public Response searchDemo(@RequestBody JSONObject body) throws IOException {
        String keyword = body.getString("keyword");
        Integer from = body.getInteger("from");
        Integer size = body.getInteger("size");

        Response response = new Response();
        Object searchResult = searchService.search(keyword, from, size);

        response.setCode(200);
        response.setMessage("success");
        response.setResult(searchResult);

        return  response;

    }

    @GetMapping("/suggest")
    public Response test(@RequestParam("text") String text) throws IOException {
        Response response = new Response();
        Object searchResult = searchService.searchText(text);
        response.setCode(200);
        response.setMessage("success");
        response.setResult(searchResult);
        return response;
    }

    @PostMapping("/searchInfo")
    public Response searchInfo(@RequestBody JSONObject body) throws IOException {

        String keyword = body.getString("keyword");
        Integer from = body.getInteger("from");
        Integer size = body.getInteger("size");

        Object search = searchService.search(keyword, from, size);
        Response response = new Response();
        response.setCode(200);
        response.setMessage("success");
        response.setResult(search);

        return response;

    }

    @RequestMapping("/suggestion")
    public Page<Suggest> suggest(String keyword){
        return searchService.getSuggest(keyword);
    }

}
