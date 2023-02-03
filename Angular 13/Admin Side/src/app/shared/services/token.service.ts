import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class TokenService{

    constructor() { }

    public getAccessToken(){
        return localStorage.getItem('accessToken') ;
    }

    public setAccessToken(accessToken : string ){
        localStorage.setItem('accessToken' , accessToken) ;
    }

    public getRefreshToken(){
        return localStorage.getItem('refreshToken') ;
    }

    public setRefreshToken(refreshToken : string ){
        localStorage.setItem('refreshToken' , refreshToken) ;
    }

    public setTokens(accessToken : string , refreshToken : string){
        localStorage.setItem('accessToken' , accessToken) ;
        localStorage.setItem('refreshToken' , refreshToken) ;
    }

}