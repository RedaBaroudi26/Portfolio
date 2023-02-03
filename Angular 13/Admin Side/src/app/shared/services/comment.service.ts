import { HttpClient, HttpContext, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IComment } from '../model/Comment';
import { WITH_TOKEN } from './token-interceptor.service';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private apiServerUrl = environment.apiBaseUrl ;

  constructor(private http : HttpClient) { }

  public addComment(newComment : IComment) : Observable<IComment>{
    return this.http.post<IComment>(`${this.apiServerUrl}/api/comment/add` , newComment ) ;
  }

  public commentCount() : Observable<number>{ 
    return this.http.get<number>(`${this.apiServerUrl}/api/comment/count` ) ;
  }

  public deleteComment( idComment : number ) : Observable<void>{
     return this.http.delete<void>(`${this.apiServerUrl}/api/comment/delete/${idComment}` ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    } ) ;
  }
  
}
