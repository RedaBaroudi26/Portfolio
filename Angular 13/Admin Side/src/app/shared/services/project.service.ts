import { HttpClient, HttpContext, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IProjectPages, IProject } from '../model/Project';
import { WITH_TOKEN } from './token-interceptor.service';

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

  public getProjectById(idProject : number) : Observable<IProject> {
    return this.http.get<IProject>(`${this.apiServerUrl}/api/project/projectById/${idProject}`) ;
  }

  public getProjectImage(idProject : number , idImage : number){
         return (`${this.apiServerUrl}/api/project/imageProject/${idImage}/${idProject}`) ; 
  }

  public addProject( newProject : FormData ) : Observable<IProject>{
     return this.http.post<IProject>(`${this.apiServerUrl}/api/project/add` , newProject ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    }) ;
  }


  public updateProject( updatedProject : FormData) : Observable<IProject>{
     return this.http.put<IProject>(`${this.apiServerUrl}/api/project/update` , updatedProject ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    } ) ;
  }

  public updateImage(updatedImage : FormData) {
     return this.http.put<void>(`${this.apiServerUrl}/api/project/updateImage` , updatedImage ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    } ) ;
  } 

  public deleteProject(idProject : number) : Observable<any> {
    return this.http.delete<any>(`${this.apiServerUrl}/api/project/delete/${idProject}` ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    }) ;
  }


}
