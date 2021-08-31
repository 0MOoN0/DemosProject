package com.test.es.apitest;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leon
 */
@SpringBootTest
public class ESIndexTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    void testCreateIndex() throws IOException, JSONException {
        CreateIndexRequest index = new CreateIndexRequest("user_index");
        // mapping的构建有多种方式,可以参考官网
        Map<String, Object> name = new HashMap<>();
        name.put("type","text");
        Map<String,Object> desc = new HashMap<String, Object>();
        desc.put("type","text");
        Map<String, Object> properties = new HashMap<>();
        properties.put("name",name);
        properties.put("desc",desc);
        HashMap<String, Object> mapping = new HashMap<>();
        mapping.put("mapping",properties);
        index.mapping(mapping);
        CreateIndexResponse res = restHighLevelClient.indices().create(index, RequestOptions.DEFAULT);
        System.out.println(res);
    }

    @Test
    void testIndexExists() throws IOException {
        GetIndexRequest req = new GetIndexRequest("spring_boot2");
        boolean exists = restHighLevelClient.indices().exists(req, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest req = new DeleteIndexRequest("spring_boot");
        AcknowledgedResponse res = restHighLevelClient.indices().delete(req, RequestOptions.DEFAULT);
        System.out.println(res.isAcknowledged());  // 输出是否删除成功
    }

    @Test
    void testClient(){
        System.out.println(restHighLevelClient);
    }

}
