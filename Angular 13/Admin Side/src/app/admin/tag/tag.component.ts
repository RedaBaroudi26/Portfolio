import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ITag, ITagPages } from 'src/app/shared/model/Tag';
import { TagService } from 'src/app/shared/services/tag.service';
import { Observable, tap } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-tag',
  templateUrl: './tag.component.html',
  styleUrls: ['./tag.component.scss']
})
export class TagComponent implements OnInit {

  Tags !: Observable<ITagPages>  ;

  tagForm !: FormGroup ;
  tagName !: FormControl ;

  selectedTag : ITag = { idTag : 0 , tagName : "" } ;

  messageError !: string ;
  loading : boolean = false ;
  

  constructor( private tagService : TagService , private cd : ChangeDetectorRef) { }

  ngOnInit(): void {

    this.getAllTags() ;

    this.tagName = new FormControl('' , Validators.required) ;
    this.tagForm = new FormGroup({
      tagName : this.tagName
    }) ;

  }

  getAllTags(offest : number = 0 , size : number = 8){
      this.Tags =  this.tagService.getTagsByPages(offest , size).pipe( tap( data => { this.loading = true  ; this.cd.detectChanges()} ) ) ;
  }

  addTag(){

    this.loading = false ;

    this.tagService.addTag(this.tagForm.value).subscribe(
      ( response : any )=> { this.loading = true ; this.tagForm.reset() ;  this.messageError = "" ; this.getAllTags() ;  document.getElementById('home-tab')?.click() ;  } , 
      ( error : HttpErrorResponse ) => { this.loading = true ; this.messageError = error.error.message ;; }
    )

  }

  deleteTag(){

    this.loading = false ;

    this.tagService.deleteTag(this.selectedTag.idTag).subscribe(
      (response : any)=> { this.loading = true ;  this.getAllTags() ; document.getElementById('btnCloseDeleteTag')?.click() ; this.messageError = ""} , 
      (error : HttpErrorResponse) => { this.loading = true ; this.messageError = error.error.message ; }
    )

  }

  updateTag(){

    this.loading = false ;

    this.tagService.updateTag(this.selectedTag).subscribe(
      ( response : any )=> { this.loading = true ;  this.getAllTags() ; document.getElementById('btnCloseEditTag')?.click() ; this.messageError = "" } , 
      ( error : HttpErrorResponse ) => { this.loading = true ;  this.messageError = error.error.message ; }
    )

  }

  counterNumber(i: number): any {
    return new Array(i);
  }

  clickButton(tag : ITag){
      this.selectedTag = tag ;
      this.messageError = "" ;
  }

}
