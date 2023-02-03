import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { IProject, IProjectPages } from 'src/app/shared/model/Project';
import { ProjectService } from 'src/app/shared/services/project.service';


@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectsComponent implements OnInit {

  projects !: Observable<IProjectPages> ;

  selectedProject : IProject = {
    idProject: 0,
    projectName: '',
    description: '',
    content: '',
    rating: 0,
    clientName: '',
    completed: false,
    webSiteUrl: '',
    completedDate: '',
    images: []
  } ;
  
  messageError !: string;
  loading : boolean = false ;

  constructor(private router : Router ,  private projectService : ProjectService , private cd : ChangeDetectorRef) { 
   }

  ngOnInit(): void {

    this.getProjects() ;

  }


  getProjects( offset : number = 0 , size : number = 8 ){
      this.projects = this.projectService.getProjectsByPages(offset , size).pipe( tap( data =>  { this.loading = true ; this.cd.detectChanges() } ) ) ;
  }


  deleteProject(){
      this.projectService.deleteProject(this.selectedProject.idProject).subscribe(
        ( response : any ) => {  this.getProjects() ; document.getElementById('CloseModalDelete')?.click() } ,
        ( error : HttpErrorResponse) => {  this.messageError = error.error.message ; }
      );
  }


  navigate(idProject:number){
      this.router.navigate(['/admin/updateProject/',idProject]) ;
  }


  counterNumber(i: number): any {
    return new Array(i);
  } 

  
}
