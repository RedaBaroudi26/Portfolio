import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ISkill } from '../model/Skill';


@Injectable({
  providedIn: 'root'
})

export class SkillService {

    private apiServerUrl = environment.apiBaseUrl ;
  
    constructor(private http : HttpClient) { }

    public getAllSkills( ) : Observable<ISkill[]>{
        return this.http.get<ISkill[]>(`${this.apiServerUrl}/api/skill/all`);
    }

    public getImage(idSkill : number , idImage : number) {
      return (`${this.apiServerUrl}/api/skill/imageskill/${idSkill}/${idImage}`) ; 
    }

}    