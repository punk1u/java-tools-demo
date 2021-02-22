package tech.punklu.elasticsearchdemo.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import tech.punklu.elasticsearchdemo.entity.es.EsBlog;

/**
 * 用于访问ES的Repository
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,Integer> {
    
}
