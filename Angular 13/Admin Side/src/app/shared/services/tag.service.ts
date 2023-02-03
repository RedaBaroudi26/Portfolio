import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ITag, ITagPages } from '../model/Tag';
import { WITH_TOKEN } from './token-interceptor.service';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  private apiServerUrl = environment.apiBaseUrl ;

  constructor(private http : HttpClient) { }

  public getTags() : Observable< ITag[] >{
    return this.http.get<ITag[]>(`${this.apiServerUrl}/api/tag/all`) ;
  }

  public getTagsByPages(offest : number , size : number) : Observable<ITagPages>{
    return this.http.get<ITagPages>(`${this.apiServerUrl}/api/tag/TagsByPages/${offest}/${size}`) ;
  }

  public tagsCount() : Observable<number>{
    return this.http.get<number>(`${this.apiServerUrl}/api/tag/count`) ;
  }

  public addTag( newTag : ITag ) : Observable<ITag> {
     return this.http.post<ITag>(`${this.apiServerUrl}/api/tag/add` , newTag , { 
        context : new HttpContext().set(WITH_TOKEN , true) 
      }) ;
  }

  public updateTag( updatedTag : ITag) : Observable<ITag>{
     return this.http.put<ITag>(`${this.apiServerUrl}/api/tag/update` , updatedTag ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
     } ) ;
  }

  public deleteTag( idTag : number ) : Observable<void>{
     return this.http.delete<void>(`${this.apiServerUrl}/api/tag/delete/${idTag}` ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    } ) ;
  }


}
