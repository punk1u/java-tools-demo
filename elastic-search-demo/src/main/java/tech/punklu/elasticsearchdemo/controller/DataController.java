package tech.punklu.elasticsearchdemo.controller;

import lombok.Data;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.punklu.elasticsearchdemo.entity.es.EsBlog;
import tech.punklu.elasticsearchdemo.entity.mysql.MysqlBlog;
import tech.punklu.elasticsearchdemo.repository.mysql.MysqlBlogRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DataController {

    @Autowired
    private MysqlBlogRepository mysqlBlogRepository;


    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 倒序查询所有博客
     * @return
     */
    @GetMapping("/blogs")
    public Object blog(){
        List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryAll();
        return mysqlBlogs;
    }

    @PostMapping("/search")
    public Object search(@RequestBody Param param){
        Map<String,Object> map = new HashMap<>();

        // 计时
        StopWatch watch = new StopWatch();
        watch.start();
        String type = param.getType();
        if (type.equalsIgnoreCase("mysql")){
            // mysql查询
            List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryBlogs(param.getKeyword());
            map.put("list",mysqlBlogs);
        }else if (type.equalsIgnoreCase("es")){
            NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
            searchQueryBuilder.withQuery(QueryBuilders.matchPhraseQuery("title",param.getKeyword()))
                    .withQuery(QueryBuilders.matchPhraseQuery("content",param.getKeyword()));
            SearchHits<EsBlog> searchResult = elasticsearchRestTemplate.search(searchQueryBuilder.build(),EsBlog.class);

            List<EsBlog> result = new ArrayList<>();
            List<SearchHit<EsBlog>> content = searchResult.getSearchHits();
            for (SearchHit<EsBlog> blog : content){
                EsBlog esBlog = blog.getContent();
                result.add(esBlog);
            }
            map.put("list",result);
        }else {
            return "param error";
        }
        watch.stop();
        long totalTimeMillis = watch.getTotalTimeMillis();
        map.put("duration",totalTimeMillis);
        return map;
    }



    @Data
    public static class Param{
        // mysql,es
        private String type;

        private String keyword;
    }
}
