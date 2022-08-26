package com.jarvis.blog.controller;

import com.jarvis.blog.VO.Result;
import com.jarvis.blog.VO.params.ArticleParam;
import com.jarvis.blog.VO.params.PageParams;
import com.jarvis.blog.common.aop.LogAnnotation;
import com.jarvis.blog.common.cache.Cache;
import com.jarvis.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //json数据进行交互
@RequestMapping("articles")
public class ArticleController {
    //查询功能
    @PostMapping("search")
    public Result search(@RequestBody ArticleParam articleParam){
        String search = articleParam.getSearch();
        return articleService.searchArticle(search);
    }

    /**
     * 首页 文章列表
     * @param pageparams
     */
    @Autowired
    private ArticleService articleService;





    @PostMapping
    //对此接口进行日志
    @LogAnnotation(module = "文章",operator = "获取文章列表")
    @Cache(expire = 5 *60 * 1000, name = "list_article")
    public Result listArticle(@RequestBody PageParams pageparams){
        return articleService.listArticle(pageparams);
    }

    /**
     * 首页最热文章
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5 *60 * 1000, name = "hot_article")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页最新文章
     */
    @PostMapping("new")
    @Cache(expire = 5 *60 * 1000, name = "new_article")
    public Result newArticle(){
        int limit = 5;
        return articleService.newArticle(limit);
    }

    /**
     * 文章归档
     */
    @PostMapping("listArchives")
    @Cache(expire = 5 *60 * 1000, name = "list_archives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    /**
     * 文章详情
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){

        return articleService.publish(articleParam);
    }

    @PostMapping("/{id}")
    public Result articleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }




}





















