package com.jarvis.blog.service;

import com.jarvis.blog.VO.Result;
import com.jarvis.blog.VO.TagVo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    //最热标签
    Result hots(int limit);

    //写文章的标签查询所有文章标签
    Result findAll();

    //标签
    Result findAllDetail();

    //标签分类文章
    Result findDetailById(Long id);
}
