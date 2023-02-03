import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Observable, tap } from 'rxjs';
import { IArticle, IArticlePages } from 'src/app/shared/model/Article';
import { ArticleService } from 'src/app/shared/services/article.service';



@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {

  articles !: Observable<IArticlePages>;
  loading : boolean = false ; 

  selectedArticle : IArticle = {
    idArticle: 0,
    title: '',
    description: '',
    content: '',
    dateCreation: '',
    writerName: '',
    category: {idCategory : 0 , categoryName : ""},
    comments: [],
    images: []
  };

  messageError !: string;

  constructor(private router: Router,  private articleService: ArticleService , private cd : ChangeDetectorRef ) {
  }

  ngOnInit(): void {
   this.getArticles(0);
  }


  getArticles(offset: number = 0 , size: number = 10 ) {
    this.articles = this.articleService.getArticlesByPages(offset, size).pipe( tap( data => { this.loading = true ; this.cd.detectChanges() } ) );
  }


  deleteArticle() {
    this.articleService.deleteArticle(this.selectedArticle.idArticle).subscribe(
      ( response : any) => {  document.getElementById('closeModal')?.click() ; this.getArticles() } ,
      ( error : HttpErrorResponse) => {  this.messageError = error.error.message ;}
    )
  }


  navigate(idArticle: number) {
    this.router.navigate(['/admin/updateArticle/', idArticle]);
  }


  counterNumber(i: number): any {
    return new Array(i);
  }

}
