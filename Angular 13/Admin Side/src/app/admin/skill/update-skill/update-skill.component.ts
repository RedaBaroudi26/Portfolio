import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ISkill } from 'src/app/shared/model/Skill';
import { SkillService } from 'src/app/shared/services/skill.service';

@Component({
  selector: 'app-update-skill',
  templateUrl: './update-skill.component.html',
  styleUrls: ['./update-skill.component.scss']
})
export class UpdateSkillComponent implements OnInit {

  skill$ !: Observable<ISkill> ; 

  imageUrl : any ;

  image : any ;

  loading : boolean = true ;
  messageError !: string ;

  constructor(private skillService : SkillService , private router : Router , private route : ActivatedRoute) { }

  ngOnInit(): void {

    this.getSkillById() ;

  }

  getSkillById(){
    this.route.paramMap.subscribe(
      params => {
         let id : number = Number(params.get('id')) ;
         this.skill$ = this.skillService.getSkillById(id) ;
      } 
   )
  }

  updateSkill( value : any ){

    this.loading = false ;
    let skill : ISkill = value ;
    this.skillService.updateSkill(skill).subscribe(
      ( response : any ) => { this.loading = true ; this.router.navigate(['admin/skill'])  } ,
      (error : HttpErrorResponse) => { this.loading = true ; this.messageError = error.error.message }
    )
  
  }

  getImage(idSkill : number | undefined ,idImage : number | undefined ){
      return  this.skillService.getImage(idSkill , idImage) ;
  }

  updateImage(idSkill : number = 0  , idImage : number ){
    
    let formData : FormData = new FormData() ;
    formData.append('idSkill' , idSkill?.toString()) ;
    formData.append('idImage' , idImage?.toString()) ;
    formData.append('file' , this.image ) ;
               
    this.skillService.updateImage(formData).subscribe(
      (response : any )=> { } ,
      (error : HttpErrorResponse) => {this.messageError = error.error.message}
       )

  }

  onSelectFile(event: any) {

    if (event.target.files.length > 0) {

      const file = event.target.files[0];

      var mimeType = event.target.files[0].type;

      if (mimeType.match(/image\/*/) == null) {
        this.messageError = "Only images are Supported";
        return;
      }

      var reader = new FileReader();
      reader.readAsDataURL(file);


      this.image = file;
      this.imageUrl = file;
      reader.onload = (_event) => {
          this.imageUrl = reader.result;
      }

    }
  }

  back(){
    this.router.navigate(['/admin/skill']) ;
  }

}
