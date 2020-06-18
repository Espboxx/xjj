package com.jb.xjjreptile.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgInfo {

    private String theme;//    主题
    private String nikeName;// 作者昵称
    private String tags;// 标签
    private Map<String,Object> urls;
}
