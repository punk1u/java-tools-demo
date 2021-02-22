package tech.punklu.elasticsearchdemo.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.punklu.elasticsearchdemo.entity.mysql.MysqlBlog;

import java.util.List;

public interface MysqlBlogRepository extends JpaRepository<MysqlBlog,Integer> {

    /**
     * 按时间倒序查询所有博客
     * @return
     */
    @Query("select e from MysqlBlog e order by e.createTime desc")
    List<MysqlBlog> queryAll();

    @Query("select e from MysqlBlog e where e.title like concat('%',:keyword,'%') or e.content like concat('%',:keyword,'%')")
    List<MysqlBlog> queryBlogs(@Param("keyword")String keyword);
}
