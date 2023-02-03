import { HttpClient, HttpContext, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IArticle, IArticlePages } from '../model/Article';
import { WITH_TOKEN } from './token-interceptor.service';


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

  public getArticleById(idArticle : number) : Observable<IArticle> {
    return this.http.get<IArticle>(`${this.apiServerUrl}/api/article/articleById/${idArticle}`) ;
  }

  public getArticleImage(idArticle : number, idImage : number){
         return (`${this.apiServerUrl}/api/article/imageArticle/${idArticle}/${idImage}`) ; 
  }

  public getArticlesCountByCategory(idCategory : number):Observable<number>{
    return this.http.get<number>(`${this.apiServerUrl}/api/article/countArticlesByCategory/${idCategory}`) ; 
}

  public addArticle( newArticle : FormData ) : Observable<IArticle>{
     return this.http.post<IArticle>(`${this.apiServerUrl}/api/article/add` , newArticle ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    });
  }

  public updateArticle( updatedArticle : FormData) : Observable<IArticle>{
     return this.http.put<IArticle>(`${this.apiServerUrl}/api/article/update` , updatedArticle ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    } ) ;
  }

  public updateImage(updatedImage : FormData) {
     return this.http.put<void>(`${this.apiServerUrl}/api/article/updateImage` , updatedImage ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    } ) ;
  } 

  public deleteArticle(idArticle : number) : Observable<void> {
     return this.http.delete<void>(`${this.apiServerUrl}/api/article/delete/${idArticle}` ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    }) ;
  }



}
