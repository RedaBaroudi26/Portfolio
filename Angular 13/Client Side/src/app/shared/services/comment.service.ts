import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IComment } from '../model/Comment';

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
    let options = {
      headers: new HttpHeaders().set('Authorization' , "Bearer "+localStorage.getItem("accessToken") ) 
     }; 
    return this.http.get<number>(`${this.apiServerUrl}/api/comment/count` , options) ;
  }
  
}
