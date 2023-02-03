import { Component , OnInit } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { IArticlePages } from 'src/app/shared/model/Article';
import { IProjectPages } from 'src/app/shared/model/Project';
import { ArticleService } from 'src/app/shared/services/article.service';
import { CategoryService } from 'src/app/shared/services/category.service';
import { CommentService } from 'src/app/shared/services/comment.service';
import { ProjectService } from 'src/app/shared/services/project.service';

@Component({
  templateUrl: './dashboard.component.html' , 
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  articles$ !: Observable<IArticlePages> ;
  projects$ !: Observable<IProjectPages> ;
  numberOfCategories$ !: Observable<number> ;
  numberOfComment$ !: Observable<number> ;
  loading : boolean  = false ;
  
  
  constructor(private categoryService : CategoryService , private commentService : CommentService , private articleService : ArticleService , private projectService : ProjectService) {}

  ngOnInit(): void {

    this.getCategorieCount() ;
    this.getCommentsCount() ;
    this.getArticles() ;
    this.getProjects() ;

  }

  getCategorieCount(){
       this.numberOfCategories$ =  this.categoryService.getCategorCount() ;
  }

  getCommentsCount(){
     this.numberOfComment$ = this.commentService.commentCount().pipe( map( ( data : number ) => { return data ==  0 ? 0 : data }  )) ;
  }

  getArticles(){
    this.articles$ = this.articleService.getArticlesByPages(0 , 4) ;
  }

  getProjects(){
    this.projects$ = this.projectService.getProjectsByPages(0 , 6).pipe( tap(data => {this.loading = true ;}) ) ;
  }

  getImage(idArticle : number , idImage : number ){
    return this.articleService.getArticleImage(idArticle , idImage) ;
  }
  
}
