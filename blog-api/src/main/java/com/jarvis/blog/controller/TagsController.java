package com.jarvis.blog.controller;


import com.jarvis.blog.VO.Result;
import com.jarvis.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //返回Json数据
@RequestMapping("tags")
public class TagsController {
    @Autowired
    private TagService tagService;
    // /tags/hot
    @GetMapping("hot")
    public Result hot(){
        int limit = 6; //查询最热标签为前六的文章
        return tagService.hots(limit);
    }

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }

}
