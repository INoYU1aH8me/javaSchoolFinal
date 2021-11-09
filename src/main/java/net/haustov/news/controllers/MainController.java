package net.haustov.news.controllers;

import net.haustov.news.model.Article;
import net.haustov.news.persistence.ArticleRepository;
import net.haustov.news.persistence.JPAArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Optional;
import java.util.zip.*;

@Controller
public class MainController {

    @Autowired
    private JPAArticleRepository articleRepository;
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "main page");
        Iterable<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("articles", articles);
        return "home";
    }
    @GetMapping("/upload")
    public String upload(Model model) {
        model.addAttribute("title", "upload page");
        return "upload";
    }

    @PostMapping("/upload")
    public String postUpload (@RequestParam("file") MultipartFile file,  Model model){
        String text="", title="";
        try (ZipInputStream zin = new ZipInputStream(file.getInputStream(), Charset.forName("UTF-16"))) {
            boolean titleEnded=false;
            if (zin.getNextEntry() != null) {
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    text+=(char) c;
                    if ((char) c == '\n' || title.length()>200)
                        titleEnded=true;
                    if(!titleEnded)
                        title+=(char)c;

                }
                zin.closeEntry();
            }
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
        if(title.length()>0) {
            Article article = new Article(title, text);
            articleRepository.save(article);
        }
        return "home";
    }

    @GetMapping("/home/{id}")
    public String newsDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Article> article=  articleRepository.findById(id);
        ArrayList<Article> res = new ArrayList<>();
        article.ifPresent(res::add);
        model.addAttribute("article", res);
        return "news-details";
    }
}