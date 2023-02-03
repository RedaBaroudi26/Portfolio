import { HttpClient, HttpContext, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment'
import { IUser } from '../model/User';
import { WITH_TOKEN } from './token-interceptor.service';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
}) 
export class UserService {

  private apiServerUrl =  environment.apiBaseUrl ;

  user !: IUser ;

  isAuth !: Observable<boolean>  ; 

  constructor(private http : HttpClient , private tokenService : TokenService) { }


  public login(username : string , password : string) : any{
    let options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };
    let body = new URLSearchParams();
        body.set('username', username);
        body.set('password', password);
    return this.http.post<any>(`${this.apiServerUrl}/login` , body , options ) ;
  }

  public profile() : Observable<IUser>{
    let username  = localStorage.getItem("username");
    return this.http.get<IUser>(`${this.apiServerUrl}/api/user/profile/${username}` ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    } ) ;
  }

  public updateProfile(user : IUser) : Observable<IUser>{
    return this.http.put<IUser>(`${this.apiServerUrl}/api/user/update` , user , { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    } ) ;
  }

  public refreshAccessToken(){
    let options = {
      headers: new HttpHeaders().set('Authorization' , "Bearer " + this.tokenService.getRefreshToken()) 
    };
    return this.http.get<any>(`${this.apiServerUrl}/api/user/refreshToken` , options ) ;
  } 
  
  public IsAuth() : Observable<boolean>{
    let username  = localStorage.getItem("username");
    return this.http.get<boolean>(`${this.apiServerUrl}/api/user/isAuth/${username}` , { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    } ) ;
  }
 

}
