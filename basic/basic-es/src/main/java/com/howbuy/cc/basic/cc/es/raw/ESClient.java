package com.howbuy.cc.basic.cc.es.raw;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by xinwei.cheng on 2015/11/12.
 */
public class ESClient {
    private final Client client = TransportClient.builder().build().
            addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("192.168.221.21" , 9300)));


    @After
    public void close() {
        client.close();
    }

    /**
     * index
     */
    @Test
    public void createIndex() throws IOException {
        for (int i = 0; i < 1000; i++) {
            Map<String, Object> json = new HashMap<String, Object>();
            json.put("user", "kimchy");
            json.put("postDate", new Date());
            json.put("message", "trying out Elasticsearch");

            // generate json
            byte[] jsonByte = JSON.toJSON(json).toString().getBytes();

            IndexResponse response = client.prepareIndex("twitter", "tweet", String.valueOf(i))
                    .setSource(jsonByte).get();
            System.out.println(response.toString());
        }
    }

    /**
     * 转换成json对象
     * @return
     */

    @Test
    public void getById() throws IOException {
        GetResponse response = client.prepareGet("twitter", "tweet", "1").get();
        System.out.println(response.getSource());
    }


    @Test
    public void getByIds(){
        MultiGetResponse multiGetItemResponses  = client.prepareMultiGet().add("twitter", "tweet", "1").add("twitter", "tweet", "2").get();
        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                String json = response.getSourceAsString();
                System.out.println(json);
            }
        }
    }


    @Test
    public void search(){
        SearchResponse response = client.prepareSearch("twitter")
                .setTypes("tweet")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery("_all", "kimchy"))                 // Query
//                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();
        Iterator<SearchHit> iterable = response.getHits().iterator();
        while(iterable.hasNext()){
            SearchHit searchHitFields = iterable.next();
            System.out.println(searchHitFields.getSourceAsString());
        }
    }


    @Test
    public void count(){
        CountResponse response = client.prepareCount("twitter")
//                .setQuery(termQuery("_type", "type1"))
                .execute()
                .actionGet();
        System.out.println(response.status() + "," + response.getCount());
    }

    @Test
    public void deleteById(){
        DeleteResponse response = client.prepareDelete("twitter", "tweet", "1")
                .get();
        System.out.println(response.isFound());
    }


    @Test
    public void update() throws ExecutionException, InterruptedException {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user", "spark");
        json.put("postDate", new Date());
        json.put("message", "trying out Elasticsearch");


        UpdateRequest updateRequest = new UpdateRequest("twitter", "tweet", "2").id("2").doc(JSON.toJSON(json).toString().getBytes());
        UpdateResponse updateResponse = client.update(updateRequest).get();
        System.out.println(updateResponse);
    }


    @Test
    public void updateForThreads() throws ExecutionException, InterruptedException {
        for(int i = 0 ; i < 1000 ; i ++) {
            new Thread() {
                public void run() {
                    while (true){
                        GetResponse getResponse = client.prepareGet("twitter", "tweet", "11").get();
                        Map<String, Object> map = getResponse.getSource();
                        map.put("count", map.get("count") == null ? 1 : (Integer) map.get("count") + 1);

                        UpdateRequest updateRequest = new UpdateRequest("twitter", "tweet", "11").version(getResponse.getVersion()).doc(JSON.toJSON(map).toString().getBytes());
                        try {
                            client.update(updateRequest).get();
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                        break;
                    }
                }
            }.start();
        }

        Thread.sleep(100000);
    }



}
