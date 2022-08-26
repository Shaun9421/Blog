package com.jarvis.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jarvis.blog.VO.CommentVo;
import com.jarvis.blog.VO.Result;
import com.jarvis.blog.VO.UserVo;
import com.jarvis.blog.VO.params.CommentParam;
import com.jarvis.blog.dao.mapper.ArticleMapper;
import com.jarvis.blog.dao.mapper.CommentMapper;
import com.jarvis.blog.dao.pojo.Article;
import com.jarvis.blog.dao.pojo.Comment;
import com.jarvis.blog.dao.pojo.SysUser;
import com.jarvis.blog.service.CommentsService;
import com.jarvis.blog.service.SysUserService;
import com.jarvis.blog.utils.UserThreadLocal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    //根据文章id查询评论列表
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 1.根据文章id查询评论列表
     * 2.根据作者id查询作者信息
     * 3.判断如果level=1 要去查询有无自评论
     * 4.有 根据评论id查询 （parent_id）
     * @param id
     * @return
     */
    @Override
    public Result commentsByArticleId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<Comment>();
        queryWrapper.eq(Comment::getArticleId, id);//根据文档id进行查询
        queryWrapper.eq(Comment::getLevel,1);//根据level = 1 的查询 再根据level 1 进行子查询
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    //用户编写评论
    @Override
    public Result comment(CommentParam commentParam) {
        //拿到当前用户
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        //如果是空，parent就是0
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        UpdateWrapper<Article> updateWrapper = Wrappers.update();
        updateWrapper.eq("id",comment.getArticleId());
        updateWrapper.setSql(true,"comment_counts=comment_counts+1");
        this.articleMapper.update(null,updateWrapper);
        CommentVo commentVo = copy(comment);
        return Result.success(commentVo);
    }


    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;


    }

    private CommentVo copy(Comment comment) {

        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = this.sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = comment.getLevel();
        if (1 == level) {
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        //toUser 给谁评论
        if (level > 1){
            Long toUid = comment.getToUid();
            UserVo toUserVo = this.sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<Comment>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel,2);

        return copyList(commentMapper.selectList(queryWrapper));
    }
}
