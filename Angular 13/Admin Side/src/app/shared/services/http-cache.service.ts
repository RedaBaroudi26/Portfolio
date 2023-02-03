import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HttpCacheService {

   requests : any = {} ;

  constructor() { }

  put(url : string , response : HttpResponse<any>){
         this.requests[url] = response ;
  }

  get(url : string ):HttpResponse<any>{
    return this.requests[url] ;
  }

  invalidateUrl(url:string){
    this.requests[url] = undefined ;
  }

  invalidateCache(){
    this.requests = {} ;
  }

}
