package com.test.es.apitest;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.es.domain.User;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Leon
 */
@SpringBootTest
public class ESDocumentTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testAddDocument() throws IOException {
        User user = new User(3,"张三");
        IndexRequest req = new IndexRequest("user_index");
        // PUT /user_index/_doc/1
        req.id("1");
        req.timeout(TimeValue.timeValueSeconds(10));
        req.timeout("1s");

        req.source(JSON.toJSONString(user), XContentType.JSON);

        IndexResponse res = restHighLevelClient.index(req, RequestOptions.DEFAULT);

        System.out.println(res.toString());
        System.out.println(res.status()); // 查看添加状态
    }

    // 判断文档是否存在
    @Test
    public void testExists() throws IOException {
        GetRequest req = new GetRequest("user_index", "2");
        // 不获取_source,提高效率
        req.fetchSourceContext(new FetchSourceContext(false));
        req.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(req, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    // 获取文档的信息
    @Test
    public void testGetDocument() throws IOException {
        GetRequest req = new GetRequest("user_index", "1");
        GetResponse res = restHighLevelClient.get(req, RequestOptions.DEFAULT);
        System.out.println(res.getSourceAsString());
        System.out.println(res);
    }

    // 更新文档信息
    @Test
    public void testUpdateDocument() throws IOException {
        UpdateRequest req = new UpdateRequest("user_index", "1");
        req.timeout("1s");
        User user = new User(18, "李四");
        req.doc(JSON.toJSONString(user),XContentType.JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        UpdateResponse res = restHighLevelClient.update(req, RequestOptions.DEFAULT);
        System.out.println(res.status());
    }

    // 删除文档
    @Test
    public void testDeleteDocument() throws IOException {
        DeleteRequest req = new DeleteRequest("user_index", "1");
        req.timeout("1s");
        DeleteResponse res = restHighLevelClient.delete(req, RequestOptions.DEFAULT);
        System.out.println(res.status());
    }

    // 批量操作-新增
    @Test
    public void testBulkRequest() throws IOException {
        BulkRequest req = new BulkRequest();
        req.timeout("10s");
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(10,"张三1"));
        users.add(new User(11,"张三2"));
        users.add(new User(12,"张三3"));
        users.add(new User(10,"李四4"));
        users.add(new User(11,"李四2"));
        users.add(new User(12,"李四3"));
        users.add(new User(13,"李四4"));
        users.add(new User(14,"李四5"));
        for (User user : users) {
            // 相当于BulkRequest里面包含多个IndexRequest
            // IndexRequest在前文中有使用过
            req.add(new IndexRequest("user_index")
                    .source(JSON.toJSONString(user),XContentType.JSON)
                    // 如果不设置id,es会自动生成一个id
            );
        }
        BulkResponse res = restHighLevelClient.bulk(req, RequestOptions.DEFAULT);
        // 判断操作是否失败,也可以使用status来判断
        System.out.println(res.hasFailures());
    }

    // 查询
    @Test
    public void testSearch() throws IOException {
        SearchRequest req = new SearchRequest("user_index");
        // 构建搜索器相当于_search下的内容{}
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 构建Query条件
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "张三");
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        req.source(sourceBuilder);
        // 请求es服务端并封装返回内容
        SearchResponse res = restHighLevelClient.search(req, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(res.getHits()));
        System.out.println("===========================");
        for (SearchHit hit : res.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }


}
