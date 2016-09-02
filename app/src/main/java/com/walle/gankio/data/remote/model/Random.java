package com.walle.gankio.data.remote.model;

import java.util.List;

/**
 * Created by void on 16/8/17.
 */
public class Random {

    public boolean error;
    public List<Results> results;

    public static class Results {
        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String type;
        public String url;
        public boolean used;
        public String who;
    }
}
