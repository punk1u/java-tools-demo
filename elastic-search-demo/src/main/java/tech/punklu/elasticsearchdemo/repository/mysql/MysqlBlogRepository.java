package tech.punklu.elasticsearchdemo.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.punklu.elasticsearchdemo.entity.mysql.MysqlBlog;

public interface MysqlBlogRepository extends JpaRepository<MysqlBlog,Integer> {

}
