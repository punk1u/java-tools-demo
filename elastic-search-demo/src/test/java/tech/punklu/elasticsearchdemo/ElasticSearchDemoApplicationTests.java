package tech.punklu.elasticsearchdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.punklu.elasticsearchdemo.entity.es.EsBlog;
import tech.punklu.elasticsearchdemo.repository.es.EsBlogRepository;

import java.util.Iterator;

@SpringBootTest
class ElasticSearchDemoApplicationTests {

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void test(){
        Iterable<EsBlog> all = esBlogRepository.findAll();
        Iterator<EsBlog> iterator = all.iterator();
        EsBlog next = iterator.next();
        System.out.println("----------" + next.getTitle());
    }

}
