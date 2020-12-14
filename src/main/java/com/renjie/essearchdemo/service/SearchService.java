package com.renjie.essearchdemo.service;

import com.renjie.essearchdemo.entity.Suggest;
import org.springframework.data.domain.Page;

import java.io.IOException;

/**
 * @Auther: fan
 * @Date: 2020/12/9
 * @Description:
 */
public interface SearchService {
   Object search(String keyword, Integer from, Integer size) throws IOException;
   Object searchText(String text) throws IOException;
   Page<Suggest> getSuggest(String keyword);
}
