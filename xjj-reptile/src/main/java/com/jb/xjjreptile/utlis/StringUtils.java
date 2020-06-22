package com.jb.xjjreptile.utlis;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {


    public static List<String> getUrlId(String urls) {
        String[] split = urls.split(",");
        List<String> strings = new ArrayList<>();
        for (String s : split) {
            //对单个处理 再进行分割
            String[] split1 = s.replace("\"", "").replace("[", "").replace("]", "").split("/");
            String replace = split1[split1.length - 1].replace(".jpg", "");
            strings.add(replace);
        }
        return strings;
    }
}
