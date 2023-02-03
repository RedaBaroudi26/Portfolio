import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import * as Aos from "aos" ;
import { Observable, tap } from 'rxjs';
import { IProject } from 'src/app/shared/model/Project';
import { ProjectService } from 'src/app/shared/services/project.service';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.scss']
})
export class PortfolioComponent implements OnInit {

  loading : boolean = false ;
  projects$ !: Observable< IProject [] >;

  constructor(private projectService : ProjectService , private title : Title) { }

  ngOnInit(): void {
   Aos.init() ;
   this.getProjects() ;
   this.title.setTitle('Portfolio') ;
  }

  getProjects(){
   this.projects$ = this.projectService.getProjects().pipe( tap( data => { this.loading = true } ) ) ;
  }

  getImage(idProject : number , idImage : number ){
    return this.projectService.getProjectImage(idProject , idImage) ;
  } 

}
