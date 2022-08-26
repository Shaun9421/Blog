package com.jarvis.blog.service;

import com.jarvis.blog.VO.Result;
import com.jarvis.blog.VO.params.ArticleParam;
import com.jarvis.blog.VO.params.PageParams;


public interface ArticleService {


    /**
     * 分页查询文章列表
     *
     * @param pageparams
     * @return
     */
    Result listArticle(PageParams pageparams);

    /**
     * 最热文章
     *
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * z最新文章
     *
     * @param limit
     * @return
     */
    Result newArticle(int limit);

    /**
     * 文章归档
     *
     * @return
     */
    Result listArchives();

    /**
     * 查看文章详情
     *
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);


    //发布文章
    Result publish(ArticleParam articleParam);

    Result searchArticle(String search);
}
