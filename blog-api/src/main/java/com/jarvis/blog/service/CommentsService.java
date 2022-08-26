package com.jarvis.blog.service;

import com.jarvis.blog.VO.Result;
import com.jarvis.blog.VO.params.CommentParam;

public interface CommentsService {

    /**
     *根据文章id查询所有评论
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);


    //用户编写评论
    Result comment(CommentParam commentParam);

}
