package com.walle.gankio.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yqh on 2016/8/10.
 */
public class SearchResult {

    /**
     * count : 1
     * error : false
     * results : [{"desc":"还在用ListView？","ganhuo_id":"57334c9d67765903fb61c418","publishedAt":"2016-05-12T12:04:43.857000","readability":"","type":"Android","url":"http://www.jianshu.com/p/a92955be0a3e","who":"陈宇明"}]
     */

    public int count;
    public boolean error;
    public List<ResultsEntity> results;

    public static class ResultsEntity {

        public String desc;
        public String _id;
        public String createdAt;
        public String publishedAt;
        @Expose(serialize = false,deserialize = false)
        public String readability;
        public String type;
        public String url;
        public String swf;
        public String who;
        public String video;
    }
}
