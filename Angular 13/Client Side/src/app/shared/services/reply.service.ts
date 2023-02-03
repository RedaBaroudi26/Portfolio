import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IReply } from '../model/Reply';

@Injectable({
  providedIn: 'root'
})
export class ReplyService {

  private apiServerUrl = environment.apiBaseUrl ;

  constructor(private http : HttpClient) { }

  public addReply(newReply : IReply) : Observable<IReply>{
    return this.http.post<IReply>(`${this.apiServerUrl}/api/reply/add` , newReply ) ;
  }

}
