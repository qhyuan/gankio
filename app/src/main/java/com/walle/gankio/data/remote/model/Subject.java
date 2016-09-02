package com.walle.gankio.data.remote.model;

import java.util.List;

/**
 * Created by yqh on 2016/8/10.
 */
public class Subject {

    /**
     * error : false
     * results : [{"_id":"57aa7d5c421aa90b3aac1edf","createdAt":"2016-08-10T09:03:24.470Z","desc":"Java8 实用字符串操作库","publishedAt":"2016-08-10T11:37:13.981Z","source":"chrome","type":"Android","url":"https://github.com/shekhargulati/strman-java","used":true,"who":"代码家"}]
     */

    public boolean error;
    public List<ResultsEntity> results;

    public static class ResultsEntity {
        /**
         * _id : 57aa7d5c421aa90b3aac1edf
         * createdAt : 2016-08-10T09:03:24.470Z
         * desc : Java8 实用字符串操作库
         * publishedAt : 2016-08-10T11:37:13.981Z
         * source : chrome
         * type : Android
         * url : https://github.com/shekhargulati/strman-java
         * used : true
         * who : 代码家
         */

        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
    }
}
