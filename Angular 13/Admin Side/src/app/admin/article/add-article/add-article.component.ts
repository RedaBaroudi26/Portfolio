import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl , FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { IArticle } from 'src/app/shared/model/Article';
import { ICategory } from 'src/app/shared/model/Category';
import { ArticleService } from 'src/app/shared/services/article.service';
import { CategoryService } from 'src/app/shared/services/category.service';

@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.scss']
})
export class AddArticleComponent implements OnInit {

  categories !: ICategory[];

  articleForm !: FormGroup;
  title !: FormControl;
  description !: FormControl;
  content !: FormControl;
  writerName !: FormControl;
  categoryName !: FormControl;

  image1: any;
  image2: any;

  imageUrl1 !: any;
  imageUrl2 !: any;

  loading : boolean = true ;
  messageError !: string;


  constructor( private categoryService: CategoryService , private articleService : ArticleService ,private router : Router) { }

  ngOnInit(): void {

    this.categoryService.categories.subscribe(
      (response: ICategory[]) => { this.categories = response; }
    )

    
    this.title = new FormControl('', [ Validators.required  , Validators.maxLength(40)]);
    this.description = new FormControl('', Validators.required);
    this.content = new FormControl('', Validators.required);
    this.writerName = new FormControl('', Validators.required);
    this.categoryName = new FormControl('', Validators.required);

    this.articleForm = new FormGroup({
      title: this.title,
      description: this.description,
      content: this.content,
      writerName: this.writerName,
      categoryName: this.categoryName
    })

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

  
  addArticle() {

    this.loading = false ;
    const formData = new FormData();
    let pipe = new DatePipe('en-US');
    const category  = this.categories.find(cat => { return cat.categoryName == this.articleForm.controls['categoryName'].value }) || this.categories[0] ;
    const article: IArticle = {
      idArticle: 0,
      title: this.articleForm.controls['title'].value,
      description: this.articleForm.controls['description'].value,
      content: this.articleForm.controls['content'].value,
      dateCreation: pipe.transform(Date.now(), 'yyyy-MM-dd HH:mm:ss') || '' ,
      writerName: this.articleForm.controls['writerName'].value,
      category: {idCategory : category.idCategory , categoryName : category.categoryName} ,
      comments: [],
      images: []
    }

    
    formData.append('files', this.image1);
    formData.append('files', this.image2);
    formData.append('article', JSON.stringify(article));

    
    this.articleService.addArticle(formData).subscribe(
      (response: any) => {
        this.loading = true ;
        this.messageError = "" ;
        this.articleForm.reset() ;
        this.imageUrl1 = "" ;
        this.imageUrl2 = "" ;
        this.image1 = "" ;
        this.image2 = "" ;
        this.router.navigate(['/admin/article']) ;
      },
      (error: HttpErrorResponse) => {   
        this.loading = true ;
         this.messageError = error.error.message ;
      }
    ) ; 


  }



}
