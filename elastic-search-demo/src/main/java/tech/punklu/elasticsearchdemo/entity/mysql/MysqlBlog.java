package tech.punklu.elasticsearchdemo.entity.mysql;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "t_blog")
@Entity
public class MysqlBlog {

    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客作者
     */
    private String author;

    /**
     * 博客内容
     */
    @Column(columnDefinition = "mediumtext") // 指定特殊字段的类型
    private String content;

    /**
     * 博客创建时间
     */
    private Date createTime;

    /**
     * 博客更新时间
     */
    private Date updateTime;
}
