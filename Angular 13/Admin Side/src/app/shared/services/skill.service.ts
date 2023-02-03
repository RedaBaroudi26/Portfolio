import { HttpClient, HttpContext, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ISkill, ISkillPage } from '../model/Skill';
import { WITH_TOKEN } from './token-interceptor.service';


@Injectable({
  providedIn: 'root'
})

export class SkillService {

    private apiServerUrl = environment.apiBaseUrl ;
  
    constructor(private http : HttpClient) { }

    public getAllSkills( ) : Observable<ISkill[]>{
        return this.http.get<ISkill[]>(`${this.apiServerUrl}/api/skill/all`);
    }

    public getSkillsByPage( offset : number | undefined , size : number | undefined ) : Observable<ISkillPage>{
      return this.http.get<ISkillPage>(`${this.apiServerUrl}/api/skill/skillByPage/${offset}/${size}`);
    }

    public getSkillById( idSkill : number | undefined ) : Observable<ISkill>{
      return this.http.get<ISkill>(`${this.apiServerUrl}/api/skill/skillById/${idSkill}`);
    }

    public addNewSkill( newSkill : FormData ) : Observable<void>{
        return this.http.post<void>(`${this.apiServerUrl}/api/skill/add` , newSkill ,  { 
          context : new HttpContext().set(WITH_TOKEN , true) 
        } );
    }

    public updateSkill( updateSkill : ISkill) : Observable<void>{
         return this.http.put<void>(`${this.apiServerUrl}/api/skill/update` , updateSkill ,  { 
          context : new HttpContext().set(WITH_TOKEN , true) 
        } ) ;
    }

    public getImage(idSkill : number | undefined, idImage : number | undefined){
        return (`${this.apiServerUrl}/api/skill/imageskill/${idSkill}/${idImage}`) ; 
    }

    public updateImage(updatedImage : FormData) {
         return this.http.put<void>(`${this.apiServerUrl}/api/skill/updateImage` , updatedImage ,  { 
          context : new HttpContext().set(WITH_TOKEN , true) 
        } ) ;
    } 
    
    public deleteSkill(idSkill : number | undefined ) : Observable<void> {
         return this.http.delete<void>(`${this.apiServerUrl}/api/skill/delete/${idSkill}` ,  { 
          context : new HttpContext().set(WITH_TOKEN , true) 
        }) ;
    }

}    