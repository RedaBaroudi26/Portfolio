import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import * as Aos from "aos" ;
import { Observable, tap } from 'rxjs';
import { ISkill } from 'src/app/shared/model/Skill';
import { SeoService } from 'src/app/shared/services/seo.service';
import { SkillService } from 'src/app/shared/services/skill.service';

@Component({
  selector: 'app-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./skills.component.scss']
})
export class SkillsComponent implements OnInit {

  loading : boolean = false ;
  skills$ !: Observable<ISkill[]> ;

  constructor(private skillService : SkillService,private seo : SeoService) { }

  ngOnInit(): void {
    Aos.init() ;
    this.getAllSkills() ;
    this.seo.updateMetaInformationForPage(' Java , Mysql , Angular , HTML5 , CSS3 , Spring Boot' , 'My skills are : Java / TypeScript (FrameWork : Spring Boot , Angular)' , 'My Skills')
  }

  getAllSkills(){
    this.skills$ =  this.skillService.getAllSkills().pipe( tap( data => { this.loading = true } ) ) ;
  }

  getImage(idSkill : number = 0 , idImage : number ){
        return this.skillService.getImage(idSkill , idImage) ;
  }


}
