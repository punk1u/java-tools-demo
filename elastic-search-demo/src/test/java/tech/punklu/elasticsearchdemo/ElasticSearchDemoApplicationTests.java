package tech.punklu.elasticsearchdemo;

import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import tech.punklu.elasticsearchdemo.entity.es.EsBlog;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ElasticSearchDemoApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void test(){
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(QueryBuilders.matchPhraseQuery("title","测试"))
                .withQuery(QueryBuilders.matchPhraseQuery("content","测试"));
        SearchHits<EsBlog> searchResult = elasticsearchRestTemplate.search(searchQueryBuilder.build(),EsBlog.class);

        List<EsBlog> result = new ArrayList<>();
        List<SearchHit<EsBlog>> content = searchResult.getSearchHits();
        for (SearchHit<EsBlog> blog : content){
            EsBlog esBlog = blog.getContent();
            result.add(esBlog);
        }
        for (EsBlog blog : result){
            System.out.println(blog.toString());
        }
    }

}
