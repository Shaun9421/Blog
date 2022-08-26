package com.jarvis.blog.dao.dos;

import lombok.Data;

@Data
public class Archives {//数据库中没有对应的表，不需要进行持久化操作
    //来源于查询语句select year(create_date) as year,month(create_date) as month,count(*) as count from ms_article group by year,month
    private String year;
    private Integer month;
    private Integer count;
}


