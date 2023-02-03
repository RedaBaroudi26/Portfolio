import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IArticle, IArticlePages } from '../model/Article';


@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private apiServerUrl = environment.apiBaseUrl ;

  constructor(private http : HttpClient) { }

  public getArticlesByPages( offset : number, size : number ) : Observable<IArticlePages>{
     return this.http.get<IArticlePages>(`${this.apiServerUrl}/api/article/articlesByProjectionAndPaggination/${offset}/${size}`);
  }

  public getArticlesByCategory( categoryName : string , offset : number, size : number ) : Observable<IArticlePages>{
    return this.http.get<IArticlePages>(`${this.apiServerUrl}/api/article/articlesByCategry/${categoryName}/${offset}/${size}`);
 }

  public getArticlesByWord( word : string , offset : number, size : number) : Observable<IArticlePages>{
    return this.http.get<IArticlePages>(`${this.apiServerUrl}/api/article/ArticlesByWord/${word}/${offset}/${size}`) ;
  }

  public getArticleByTitle(title:string):Observable<IArticle>{
    return this.http.get<IArticle>(`${this.apiServerUrl}/api/article/findByTitle/${title}`) ;
  }

  public getArticleById(idArticle : number) : Observable<IArticle> {
    return this.http.get<IArticle>(`${this.apiServerUrl}/api/article/articleById/${idArticle}`) ;
  }

  public getArticleImage(idArticle : number, idImage : number){
         return (`${this.apiServerUrl}/api/article/imageArticle/${idArticle}/${idImage}`) ; 
  }

  public getArticlesCountByCategory(idCategory : number):Observable<number>{
    return this.http.get<number>(`${this.apiServerUrl}/api/article/countArticlesByCategory/${idCategory}`) ; 
}


}
