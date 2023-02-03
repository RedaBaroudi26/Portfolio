import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { IProject } from 'src/app/shared/model/Project';
import { ProjectService } from 'src/app/shared/services/project.service';

@Component({
  selector: 'app-portfolio-details',
  templateUrl: './portfolio-details.component.html',
  styleUrls: ['./portfolio-details.component.scss']
})
export class PortfolioDetailsComponent implements OnInit {

  loading : boolean = false;
  selectedProject$ !: Observable< IProject > ;

  constructor(private title : Title ,private projectService : ProjectService , private router : ActivatedRoute) { }

  ngOnInit(): void {
     this.getProject() ;
  }

  getProject(){
    this.router.paramMap.subscribe(
        ( params ) => { let projectName : string = params.get('projectName') ; this.selectedProject$ = this.projectService.getProjectByName(projectName).pipe( tap( data => { this.loading = true } ) ) ;  this.title.setTitle(projectName) ; }
    );
  }

  getImage(idProject : number , idImage : number ){
    return this.projectService.getProjectImage(idProject , idImage) ;
  } 

  counterNumber(i: number): any {
    return new Array(i);
  } 

}
