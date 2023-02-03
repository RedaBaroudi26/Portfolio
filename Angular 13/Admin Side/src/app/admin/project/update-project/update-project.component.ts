import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { IProject } from 'src/app/shared/model/Project';
import { ProjectService } from 'src/app/shared/services/project.service';


@Component({
  selector: 'app-update-project',
  templateUrl: './update-project.component.html',
  styleUrls: ['./update-project.component.scss']
})
export class UpdateProjectComponent implements OnInit {

  updatedProject !: Observable<IProject> ;

  image1 : any ;
  image2 : any ;
 
  imageUrl1 !: any ;
  imageUrl2 !: any ;
  
  loading : boolean = true ;
  messageError !: string;

  constructor(private router : Router  , private route : ActivatedRoute , private projectService : ProjectService ) { }

  ngOnInit(): void {
   
    this.getProject() ;
    
  }

  getProject(){

    let id : number ;

    this.route.paramMap.subscribe(
      params => {    id = Number(params.get('id')) ;
                     this.updatedProject = this.projectService.getProjectById(id) ;
                } 
    ) ;

  }

  back(){
    this.router.navigate(['/admin/project']) ;
  }


  onSelectFile(event: any, imageNum: number) {

    if (event.target.files.length > 0) {

      const file = event.target.files[0];

      var mimeType = event.target.files[0].type;

      if (mimeType.match(/image\/*/) == null) {
        this.messageError = "Only images are Supported";
        return;
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

  updateProject(formValue : any){
    
    this.loading = false ;
    const formData = new FormData();

    let pipe = new DatePipe("en-US") ;

    const project : IProject = {
      idProject: formValue.idProject ,
      projectName: formValue.projectName ,
      description: formValue.description ,
      content: formValue.content ,
      rating: formValue.rating ,
      clientName: formValue.clientName,
      completed: formValue.completed ,
      webSiteUrl: formValue.webSiteUrl ,
      completedDate: pipe.transform(formValue.completedDate , 'yyyy-MM-dd') || '' ,
      images: []
    }; 

    formData.append('project' , JSON.stringify(project) ) ;

   
    this.projectService.updateProject(formData).subscribe(
      (response : IProject) => { this.loading = true ; this.router.navigate(['/admin/project']) ; } , 
      (error : HttpErrorResponse) => { this.loading = true ; this.messageError = error.error.message ; }
    )
     

  }

  updateImage( idProject : number , idImage : number ,  numImage : number ){
     
    let imageFile = (numImage == 1) ? this.image1 : this.image2 ;
    let formData = new FormData() ;

    formData.append("file" , imageFile) ;
    formData.append("idProject" , idProject.toString() ) ;
    formData.append("idImage" , idImage.toString() ) ;


    this.projectService.updateImage(formData).subscribe(
         (response : any) => {  this.router.navigate(['/admin/project'])} , 
         (error : HttpErrorResponse) => {  this.messageError = error.error.message ;} 
    ) ;

  }

  getImage(idArticle : any , idImage : any ){
    return this.projectService.getProjectImage(idArticle , idImage) ;
  }


}
