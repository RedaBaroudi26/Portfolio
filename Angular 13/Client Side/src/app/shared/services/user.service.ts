
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment'
import { IUser } from '../model/User';

@Injectable({
  providedIn: 'root'
}) 
export class UserService {

  private apiServerUrl =  environment.apiBaseUrl ;

  user$ !: Observable<IUser> ;

  constructor(private http : HttpClient) { }

  public profile() : Observable<IUser>{
    return this.http.get<IUser>(`${this.apiServerUrl}/api/user/getProfile` ) ;
  }

}