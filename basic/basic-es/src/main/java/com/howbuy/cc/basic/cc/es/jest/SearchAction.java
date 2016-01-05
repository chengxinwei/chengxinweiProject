//package com.howbuy.cc.basic.cc.es.jest;
//
//import org.junit.Test;
//
//import java.util.List;
//
///**
// * Created by xinwei.cheng on 2015/11/12.
// */
//public class SearchAction {
//
//    private SearchService searchService = new SearchService();
//
//    /**
//     * 创建news索引
//     */
//    @Test
//    public void buildSearchIndex() {
//        searchService.builderSearchIndex();
//    }
//
//    /**
//     * 搜索新闻
//     */
//    @Test
//    public void searchNews() {
//        String param = "个人";
//        List<News> news = searchService.searchsNews(param);
//        System.out.println("id   标题    内容");
//        for (int i = 0; i < news.size(); i++) {
//            News article = news.get(i);
//            System.out.println(article.getId() + "   " + article.getTitle() + "   " + article.getContent());
//        }
//    }
//
//
//    /**
//     * 搜索新闻
//     */
//    @Test
//    public void searchByField() {
//        List<News> news = searchService.searchByField("id", 1, 2, 3, 4);
//        System.out.println("id   标题    内容");
//        for (int i = 0; i < news.size(); i++) {
//            News article = news.get(i);
//            System.out.println(article.getId() + "   " + article.getTitle() + "   " + article.getContent());
//        }
//    }
//
//
//
//    /**
//     * 搜索新闻
//     */
//    @Test
//    public void searchRange() {
//        List<News> news = searchService.searchRange("id", 100, 200);
//        System.out.println("id   标题    内容");
//        for (int i = 0; i < news.size(); i++) {
//            News article = news.get(i);
//            System.out.println(article.getId() + "   " + article.getTitle() + "   " + article.getContent());
//        }
//    }
//}
