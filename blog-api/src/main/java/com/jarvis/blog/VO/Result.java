package com.jarvis.blog.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //添加全参构造
@NoArgsConstructor
public class Result {

    private  boolean success;

    private  int code;

    private  String msg;

    private  Object data;

    //成功返回
    public static Result success(Object data) {

        return new Result(true,200, "success", data);

    }

    //失败返回
    public static Result fail(int code, String msg) {
        return new Result(false,code, msg, null);
    }

}
