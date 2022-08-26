package com.jarvis.blog.VO.params;

import com.jarvis.blog.VO.CategoryVo;
import com.jarvis.blog.VO.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;

    private String Search;

}
