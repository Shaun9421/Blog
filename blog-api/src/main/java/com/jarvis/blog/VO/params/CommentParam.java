package com.jarvis.blog.VO.params;

import lombok.Data;

//评论参数对象
@Data
public class CommentParam {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}