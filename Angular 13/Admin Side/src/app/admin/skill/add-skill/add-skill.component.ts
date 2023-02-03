import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ISkill } from 'src/app/shared/model/Skill';
import { SkillService } from 'src/app/shared/services/skill.service';

@Component({
  selector: 'app-add-skill',
  templateUrl: './add-skill.component.html',
  styleUrls: ['./add-skill.component.scss']
})
export class AddSkillComponent implements OnInit {
  
  skillForm !: FormGroup ;
  skillName !: FormControl ;
  percentage !: FormControl ;

  loading : boolean = true ;
  messageError: string | undefined;
  image: any ;
  imageUrl: any;

  constructor(private skillService : SkillService , private router : Router) { }

  ngOnInit(): void {

    this.skillName = new FormControl( '' , Validators.required ) ;
    this.percentage = new FormControl( 0 , Validators.required ) ;
    this.skillForm = new FormGroup({
        skillName :   this.skillName , 
        percentage : this.percentage   
    });

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

  addSkill(){
    
    this.loading = false ;
    let newSkill : ISkill  = {
      skillName: this.skillForm.controls['skillName'].value ,
      percentage: this.skillForm.controls['percentage'].value ,
    }

    let formData : FormData = new FormData() ;
    formData.append("skill" ,JSON.stringify(newSkill) ) ;
    formData.append('file' , this.image) ;

    this.skillService.addNewSkill(formData).subscribe(
      (response : any) => { this.loading = true ; this.router.navigate(['/admin/skill']) } ,
      (error: HttpErrorResponse) => { this.loading = true ;  this.messageError = error.error.message }
    );

  }

}
