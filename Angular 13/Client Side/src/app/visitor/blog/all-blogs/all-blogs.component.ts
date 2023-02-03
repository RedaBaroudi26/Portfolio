import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import * as Aos from 'aos';
import { Observable, Subscription } from 'rxjs';
import { IArticlePages } from 'src/app/shared/model/Article';
import { IUser } from 'src/app/shared/model/User';
import { ArticleService } from 'src/app/shared/services/article.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-blog',
  templateUrl: './all-blogs.component.html'
})

export class AllBlogsComponent implements OnInit {

  articlePage$ !: Observable<IArticlePages>  ;

  sub !: Subscription ;

  private _word: string = ""; 

  public get word(): string {
    return this._word;
  }

  public set word(value: string) {
    this.categoryName = "" ;
    this._word = value;
  }

  categoryName : string = ""  ;

  constructor(private articleService : ArticleService , private route : ActivatedRoute ,private title : Title ) { }

  ngOnInit(): void {
   
    this.title.setTitle('Blogs') ;
    Aos.init() ;
    this.getArticles() ;

  }

  getArticles(offset : any = 0 , size : any = 3){

    if(this.categoryName != ""){
       this.articlePage$ = this.articleService.getArticlesByCategory(this.categoryName , offset , size) ;
       this._word = "" ;
    }else if( this.word != "" ) {
        this.articlePage$ = this.articleService.getArticlesByWord(this.word , offset , size) ; 
        this.categoryName = "" ;
    } else {
        this.articlePage$ = this.articleService.getArticlesByPages(offset ,size) ;
    } 
  
  }

  counter(i: number) {
    return new Array(i);
  }

  getImage(idArticle : number , idImage : number ){
    return this.articleService.getArticleImage(idArticle , idImage) ;
  } 

  scroll(){
    window.scroll({ 
      top: 800 , 
      left: 0, 
      behavior: 'smooth' 
    });
  }

  

}
