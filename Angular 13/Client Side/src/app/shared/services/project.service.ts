import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IProjectPages, IProject } from '../model/Project';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private apiServerUrl = environment.apiBaseUrl ;

  projects : Observable< IProject[] > = this.getProjects() ;

  constructor(private http : HttpClient) { }

  public getProjects( ) : Observable<IProject[]>{
    return this.http.get<IProject[]>(`${this.apiServerUrl}/api/project/all`) ;
  }

  public getProjectsByPages( offset : number, size : number ) : Observable<IProjectPages>{
     return this.http.get<IProjectPages>(`${this.apiServerUrl}/api/project/projectsByPagesAndProjection/${offset}/${size}`) ;
  }

  public getProjectsByWord( word : string , offset : number, size : number) : Observable<IProjectPages>{
    return this.http.get<IProjectPages>(`${this.apiServerUrl}/api/project/projectsByWordAndProjection/${word}/${offset}/${size}`) ;
  }

  public getProjectByName( projectName : string ) : Observable<IProject>{
    return this.http.get<IProject>(`${this.apiServerUrl}/api/project/projectByName/${projectName}`) ;
  }

  public getProjectById(idProject : number) : Observable<IProject> {
    return this.http.get<IProject>(`${this.apiServerUrl}/api/project/projectById/${idProject}`) ;
  }

  public getProjectImage(idProject : number , idImage : number){
         return (`${this.apiServerUrl}/api/project/imageProject/${idImage}/${idProject}`) ; 
  }


}
