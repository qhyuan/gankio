package com.walle.gankio.data.remote.model;

import java.util.List;

/**
 * Created by yqh on 2016/8/10.
 */
public class History {

    /**
     * error : false
     * results : [{"_id":"57aaa0b2421aa90c1dcbcbb1","content":"","publishedAt":"2016-08-10T11:29:00.0Z","title":"今日力推：Linus TED 演讲 / iOS 图片剪裁辅助工具 / iOS 文件变化监测 / Android Loading 动画效果 / 漂亮的 Android 引导效果框架"}]
     */

    public boolean error;
    public List<ResultsEntity> results;

    public static class ResultsEntity {
        /**
         * _id : 57aaa0b2421aa90c1dcbcbb1
         * content :
         * publishedAt : 2016-08-10T11:29:00.0Z
         * title : 今日力推：Linus TED 演讲 / iOS 图片剪裁辅助工具 / iOS 文件变化监测 / Android Loading 动画效果 / 漂亮的 Android 引导效果框架
         */

        public String _id;
        public String content;
        public String publishedAt;
        public String title;
    }
}
