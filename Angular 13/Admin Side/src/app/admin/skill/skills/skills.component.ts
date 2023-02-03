import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { ISkill, ISkillPage } from 'src/app/shared/model/Skill';
import { SkillService } from 'src/app/shared/services/skill.service';

@Component({
  selector: 'app-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./skills.component.scss']
})
export class SkillsComponent implements OnInit {

  skills !: Observable<ISkillPage>;

  messageError !: string;

  loading : boolean = false ;

  skillSelected: ISkill = {
    idSkill: 0,
    skillName: "",
    percentage: 0,
    image: undefined
  };

  constructor(private skillService: SkillService, private router: Router ,private cd : ChangeDetectorRef) { }

  ngOnInit(): void {
    this.getAllSkills() ;
  }

  getAllSkills(offset: number = 0, size: number = 8) {
    this.skills = this.skillService.getSkillsByPage(offset,size).pipe( tap( data => { this.loading = true ; this.cd.detectChanges() } ) )  ;
  }

  navigate(idSkill: number | undefined) {
    this.router.navigate(['/admin/updateSkill', idSkill]);
  }

  deleteSkill() {
    this.skillService.deleteSkill(this.skillSelected.idSkill).subscribe(
      (response: any) => { this.getAllSkills(); document.getElementById('btnCloseDeleteSkill')?.click() },
      (error: HttpErrorResponse) => { this.messageError = error.error.message }
    )
  }

  clickButton(skill : ISkill){
    this.skillSelected = skill ;
    this.messageError = "" ;
  }

  counterNumber(i: number): any {
    return new Array(i);
  }


}
