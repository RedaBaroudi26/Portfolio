import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, TemplateRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { IArticle } from 'src/app/shared/model/Article';
import { ICategory } from 'src/app/shared/model/Category';
import { ArticleService } from 'src/app/shared/services/article.service';
import { CategoryService } from 'src/app/shared/services/category.service';
import { CommentService } from 'src/app/shared/services/comment.service';
import { ReplyService } from 'src/app/shared/services/reply.service';

@Component({
  selector: 'app-update-article',
  templateUrl: './update-article.component.html',
  styleUrls: ['./update-article.component.scss']
})
export class UpdateArticleComponent implements OnInit {

  articleUpdated !: Observable<IArticle> ;

  categories !: ICategory [] ;

  image1 : any ;
  image2 : any ;
 
  imageUrl1 !: any ;
  imageUrl2 !: any ;

  loading : boolean = true ;
  messageError !: string ; 

  constructor(private router : Router , private route : ActivatedRoute , private articleService : ArticleService , private categoryService : CategoryService , private commentService : CommentService , private replyService : ReplyService ) { }

  ngOnInit(): void {

    this.getCategories() ;
    this.getArticle() ;

  }

  back(){
    this.router.navigate(['/admin/article']) ;
  }

  getArticle(){

    let id : number ;

    this.route.paramMap.subscribe(
      params => {   id = Number(params.get('id')) ;
                    this.articleUpdated = this.articleService.getArticleById(id) }
    ) ;

  } 

  getCategories(){
    this.categoryService.categories.subscribe(
        (response : ICategory [] ) => { this.categories = response } , 
        (error : HttpErrorResponse) => { this.messageError = error.error.message }
    ) ;
  }

  updateArticle(value : any){
    
    this.loading = false ;
    let category = this.categories.find( cat => cat.categoryName == value.categoryName ) || this.categories[0] ;
    let pipe = new DatePipe('en-US');
    const formData = new FormData();
    
    let article : IArticle = {
       idArticle: value.idArticle,
       title: value.title ,
       description: value.description ,
       content: value.content ,
       dateCreation: pipe.transform(value.dateCreation, 'yyyy-MM-dd HH:mm:ss') || ''  ,
       writerName: value.writerName ,
       category : category ,
       comments: [] ,
       images: []
    }

    formData.append('article' , JSON.stringify(article) ) ;
    
    this.articleService.updateArticle(formData).subscribe(
       (response : IArticle) => { this.loading = true ; this.router.navigate(['admin/article'])  } , 
       (error : HttpErrorResponse) => {  this.loading = true ; this.messageError = error.error.message ;  }
    ) 

  }

  updateImage( idArticle : number , idImage : number ,  numImage : number ){
     
       let imageFile = (numImage == 1) ? this.image1 : this.image2 ;
       let formData = new FormData() ;

       formData.append("file" , imageFile) ;
       formData.append("idArticle" , idArticle.toString() ) ;
       formData.append("idImage" , idImage.toString() ) ;

       this.articleService.updateImage(formData).subscribe(
            (response : any) => { this.router.navigate(['admin/article']) } , 
            (error : HttpErrorResponse) => {  this.messageError = error.error.message ;  } 
       ) ;

  }

  onSelectFile(event : any , imageNum : number){

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

  getImage(idArticle : number , idImage : number ){
        return this.articleService.getArticleImage(idArticle , idImage) ;
  }

  deleteComment(idComment : number){
    this.commentService.deleteComment(idComment).subscribe(
      (response : any) => {  this.router.navigate(['/admin/article']) } ,
      ( error : HttpErrorResponse ) =>  {  this.messageError = error.error.message ;  }
    )
  }

  deleteReply(idReply : number){
    this.replyService.deleteReply(idReply).subscribe(
      ( response : any ) => {  this.router.navigate(['/admin/article']) } ,
      ( error : HttpErrorResponse ) => {  this.messageError =  error.error.message ; }
    )
  }

  isTemplate(toast : any) {
		return toast.textOrTpl instanceof TemplateRef;
	}

}
