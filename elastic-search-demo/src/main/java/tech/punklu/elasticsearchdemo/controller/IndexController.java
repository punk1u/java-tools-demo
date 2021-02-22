package tech.punklu.elasticsearchdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.punklu.elasticsearchdemo.entity.mysql.MysqlBlog;
import tech.punklu.elasticsearchdemo.repository.mysql.MysqlBlogRepository;

import java.util.List;

@Controller
public class IndexController {


    @Autowired
    private MysqlBlogRepository mysqlBlogRepository;

    @RequestMapping("/")
    public String index(){
        List<MysqlBlog> all = mysqlBlogRepository.findAll();
        System.out.println(all.size());
        return "index.html";
    }
}
