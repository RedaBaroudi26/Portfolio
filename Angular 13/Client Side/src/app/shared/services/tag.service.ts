import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ITag, ITagPages } from '../model/Tag';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  private apiServerUrl = environment.apiBaseUrl ;

  constructor(private http : HttpClient) { }


  public getTags() : Observable<ITag[]>{
    return this.http.get<ITag[]>(`${this.apiServerUrl}/api/tag/all`) ;
  }

  public getTagsByPages(offest : number , size : number) : Observable<ITagPages>{
    return this.http.get<ITagPages>(`${this.apiServerUrl}/api/tag/TagsByPages/${offest}/${size}`) ;
  }

  public tagsCount() : Observable<number>{
    return this.http.get<number>(`${this.apiServerUrl}/api/tag/count`) ;
  }


}
