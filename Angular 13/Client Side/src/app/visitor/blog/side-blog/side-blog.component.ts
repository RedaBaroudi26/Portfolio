import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { IArticlePages } from 'src/app/shared/model/Article';
import { ICategory } from 'src/app/shared/model/Category';
import { ITag } from 'src/app/shared/model/Tag';
import { IUser } from 'src/app/shared/model/User';
import { ArticleService } from 'src/app/shared/services/article.service';
import { CategoryService } from 'src/app/shared/services/category.service';
import { TagService } from 'src/app/shared/services/tag.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-side-blog',
  templateUrl: './side-blog.component.html'
})
export class SideBlogComponent implements OnInit {

  categories$ !: Observable<ICategory[]> ;

  user$ !: Observable<IUser> ;

  @Output() eventCategory  = new EventEmitter<string>() ;

  @Output() eventWord  = new EventEmitter<string>() ;

  tags$ !: Observable<ITag[]> ;

  articlePage$ !: Observable<IArticlePages> ; 

  hide : boolean = false ;

  constructor(private articleService : ArticleService, private categoryService : CategoryService , private tagService : TagService , private router : Router ,private userService : UserService) { }

  ngOnInit(): void {
    this.getArticles() ;
    this.getCategories() ;
    this.getTags() ;

    if(this.router.url != "/visitor/blog")
    this.hide = true ;

    this.user$ = this.userService.user$ ;

  }

  getArticles(offset : number = 0 , size : number = 4){
    this.articlePage$ = this.articleService.getArticlesByPages(offset , size) ;
  }

  getCategories(){
     this.categories$ = this.categoryService.getCategories() ;
  }

  getTags(){
    this.tags$ = this.tagService.getTags() ;
  }
  
  getImage(idArticle : number , idImage : number ){
    return this.articleService.getArticleImage(idArticle , idImage) ;
  } 

  filterByCategory(categoryName : string){
      if(this.router.url == "/visitor/blog" )
      this.eventCategory.emit(categoryName) ;
      else
      this.router.navigate(['/visitor/blog']) ;
  }

  filterByWord(word : string){
    if(this.router.url == "/visitor/blog" )
    this.eventWord.emit(word) ;
    else{
    this.router.navigate(['/visitor/blog']) ;
    this.eventWord.emit(word) ;
    }
  }


}
