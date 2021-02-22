package tech.punklu.elasticsearchdemo.entity.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
/** Elastic Search的注解，用于指定ES中对应的索引名字和文档类型
 * useServerConfiguration:是否使用线上ES Server内的的index的mapping和配置
 * createIndex:是否在启动项目时重新在ES中创建这个索引
 */
@Document(indexName = "blog",type = "doc",useServerConfiguration = true,createIndex = false)
public class EsBlog {

    /**
     * 自增id
     */
    // 使用的是org.springframework.data.annotation包下的Id注解而不是javax下的
    @Id
    private Integer id;

    /**
     * 博客标题
     */
    // Field用于标注字段在ES中的类型及对应的分词器以及日期格式化等
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title;

    /**
     * 博客作者
     */
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String author;

    /**
     * 博客内容
     */
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String content;

    /**
     * 博客创建时间
     */
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date createTime;

    /**
     * 博客更新时间
     */
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date updateTime;
}
