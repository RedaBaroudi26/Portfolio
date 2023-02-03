import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { IProject } from 'src/app/shared/model/Project';
import { ProjectService } from 'src/app/shared/services/project.service';

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.scss']
})
export class AddProjectComponent implements OnInit {

  projectForm !: FormGroup;
  projectName !: FormControl;
  description !: FormControl;
  content !: FormControl;
  completedDate !: FormControl;
  clientName !: FormControl;
  rating !: FormControl;
  completed !: FormControl ;
  webSiteUrl !: FormControl;

  image1 : any ;
  image2 : any ;
 
  imageUrl1 !: any ;
  imageUrl2 !: any ;

  loading : boolean = true ;

  messageError !: string;

  constructor( private projectService : ProjectService , private router : Router ) { }

  ngOnInit(): void {

    this.projectName = new FormControl('',  Validators.required );
    this.description = new FormControl('', Validators.required);
    this.completedDate = new FormControl( ' ' , Validators.required);
    this.completed = new FormControl( false , Validators.required );
    this.content = new FormControl('', Validators.required);
    this.clientName = new FormControl('', Validators.required);
    this.rating = new FormControl( 3 , Validators.required);
    this.webSiteUrl = new FormControl( '' , Validators.required);

    
    this.projectForm = new FormGroup({
      projectName : this.projectName,
      description : this.description,
      content : this.content,
      completed : this.completed ,
      completedDate : this.completedDate ,
      clientName : this.clientName,
      webSiteUrl : this.webSiteUrl,
      rating : this.rating
    })

  }

  addProject() {


    this.loading = false ;
    const formData = new FormData() ;
    
    const project : IProject  = {
      idProject: 0 ,
      projectName: this.projectForm.controls['projectName'].value ,
      description: this.projectForm.controls['description'].value ,
      content: this.projectForm.controls['content'].value ,
      rating: this.projectForm.controls['rating'].value ,
      clientName: this.projectForm.controls['clientName'].value ,
      completed: this.projectForm.controls['completed'].value ,
      webSiteUrl: this.projectForm.controls['webSiteUrl'].value ,
      completedDate : ( this.projectForm.controls['completedDate'].value == " " ) ? undefined : this.projectForm.controls['completedDate'].value ,
      images: []
    } ;
   
    
    formData.append('project' , JSON.stringify(project))  ;
    formData.append('files' , this.image1) ;
    formData.append('files' , this.image2) ;

    
    this.projectService.addProject(formData).subscribe(
       (response : any) => {
        this.loading = true ;
        this.messageError = "" ;
        this.projectForm.reset();
        this.imageUrl1 = "";
        this.imageUrl2 = "";
        this.image1 = "";
        this.image2 = "";
        this.router.navigate(['/admin/project']) ;
       } ,
       (error : HttpErrorResponse) => {
          this.loading = true ;
          this.messageError = error.error.message ;
       }

    ) ;
    

  }

  
  onSelectFile(event: any, imageNum: number) {

    if (event.target.files.length > 0) {

      const file = event.target.files[0];

      var mimeType = event.target.files[0].type;

      if (mimeType.match(/image\/*/) == null) {
        this.messageError = "Only images are Supported";
        return ;
      }

      var reader = new FileReader();
      reader.readAsDataURL(file);

      if (imageNum == 1) {
        this.image1 = file;
        this.imageUrl1 = file;
        reader.onload = (_event) => {
          this.imageUrl1 = reader.result;
        }
      } else {
        this.image2 = file;
        this.imageUrl2 = file;
        reader.onload = (_event) => {
          this.imageUrl2 = reader.result;
        }
      }

    }

  }

}
