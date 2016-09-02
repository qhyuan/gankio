package com.walle.gankio.data.local.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by void on 16/8/17.
 */
@Entity
public class Collect {

    private long id;
    private String desc;
    @Id
    private String url;
    private long collect_date;


    @Generated(hash = 1302040638)
    public Collect(long id, String desc, String url, long collect_date) {
        this.id = id;
        this.desc = desc;
        this.url = url;
        this.collect_date = collect_date;
    }

    @Generated(hash = 1726975718)
    public Collect() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCollect_date() {
        return collect_date;
    }

    public void setCollect_date(long collect_date) {
        this.collect_date = collect_date;
    }
}
