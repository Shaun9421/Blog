package com.jarvis.blog.service;

import com.jarvis.blog.VO.CategoryVo;
import com.jarvis.blog.VO.Result;

import java.util.List;

public interface CategoryService {
    CategoryVo findCategoryById(Long id);


    Result findAll();

    //文章分类
    Result findAllDetail();

    Result categoryDetailById(Long id);

    //文章分类

}
