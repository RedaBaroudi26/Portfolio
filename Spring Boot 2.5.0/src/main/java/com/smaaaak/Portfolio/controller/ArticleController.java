package com.smaaaak.Portfolio.controller;

import com.smaaaak.Portfolio.model.Article;
import com.smaaaak.Portfolio.model.projection.ArticleProjection;
import com.smaaaak.Portfolio.service.ArticleService;
import com.smaaaak.Portfolio.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/article")
public class ArticleController {

    private ArticleService articleService ;
    private ImageService imageService ;

    public ArticleController(ArticleService articleService, ImageService imageService) {
        this.articleService = articleService;
        this.imageService = imageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Article>> getAllArticle(){
        return new ResponseEntity<>(this.articleService.getAllArticles() , HttpStatus.OK) ;
    }

    @GetMapping("/articlesByCategry/{categoryName}/{offset}/{size}")
    public ResponseEntity<Page<ArticleProjection>> getArticlesByCategory(@PathVariable("categoryName") String categoryName, @PathVariable("offset") int offset , @PathVariable("size") int size){
        return new ResponseEntity<>(this.articleService.getArticleByProjectionAndPagesAndByCategory(categoryName , offset , size) , HttpStatus.OK) ;
    }

    @GetMapping("/findByTitle/{title}")
    public ResponseEntity<Article> getArticleByTitle(@PathVariable("title") String title){
          return  new ResponseEntity<>(this.articleService.getArticleByTitle(title),HttpStatus.OK) ;
    }

    @GetMapping("/articlesByPages/{offset}/{size}")
    public ResponseEntity<Page<Article>> getArticlesByPagination(@PathVariable("offset") int offset , @PathVariable("size") int size){
        return new ResponseEntity<>(this.articleService.getArticleByPage(offset, size) , HttpStatus.OK) ;
    }

    @GetMapping("/articlesByProjectionAndPaggination/{offset}/{size}")
    public ResponseEntity<Page<ArticleProjection>> getArticlesByProjection(@PathVariable("offset") int offset , @PathVariable("size") int size){
        return new ResponseEntity<>(this.articleService.getArticleByProjectionAndPages(offset, size) , HttpStatus.OK) ;
    }

    @GetMapping("/articleById/{id}")
    public ResponseEntity<Article> getArticlesByPagination(@PathVariable("id") Long idArticle ){
        return new ResponseEntity<>(this.articleService.getArticleByIdArticle(idArticle) , HttpStatus.OK) ;
    }

    @GetMapping("/ArticlesByWord/{word}/{offset}/{size}")
    public ResponseEntity<Page<ArticleProjection>> getArticlesByWord(@PathVariable("word") String word , @PathVariable("offset") int offset , @PathVariable("size") int size){
        return new ResponseEntity<>(this.articleService.getArticlesByWordAndPageAndProjection(word , offset , size) , HttpStatus.OK) ;
    }

    @GetMapping("/imageArticle/{idArticle}/{idImage}")
    public byte[] getPhoto(@PathVariable("idImage") Long idImage ,@PathVariable("idArticle") Long idArticle ) throws Exception{
       return  this.imageService.getImage(idImage , idArticle , "Article") ;
    }

    @PutMapping("/updateImage")
    public ResponseEntity<?> updateImage(@RequestParam("file") MultipartFile file , @RequestParam("idArticle") Long idArticle , @RequestParam("idImage") Long idImage){
        this.imageService.updateImage(idImage , idArticle , "Article" , file) ;
        return new ResponseEntity<>(HttpStatus.OK) ;
    }


    @PostMapping("/add")
    public ResponseEntity<Article> createArticle(@RequestParam("files") MultipartFile[] files , @RequestParam("article") String article ){
        return new ResponseEntity<>(this.articleService.addNewArticle(files , article ) , HttpStatus.CREATED) ;
    }

    @PutMapping("/update")
    public ResponseEntity<Article> updateArticle(@RequestParam("article") String article){
        return new ResponseEntity<>(this.articleService.updateArticle(article) , HttpStatus.OK) ;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable("id") Long idArticle){
        this.articleService.deleteArticle(idArticle); ;
        return new ResponseEntity<>(HttpStatus.OK) ;
    }


}
