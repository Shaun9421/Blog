package com.jarvis.blog.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class TagVo { //相当于和页面交互数据

//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String tagName;

    private String avatar;
}
