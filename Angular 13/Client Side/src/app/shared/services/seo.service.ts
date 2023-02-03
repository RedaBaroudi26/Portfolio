import { Injectable } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';


@Injectable({
  providedIn: 'root'
})

export class SeoService {

   private readonly appTitle : string = 'Create Your Professional Website With Smaaaak' ;
   private readonly description : string = 'Create Your Professional Website To introduce Your Company or Services For others people' ;
   private readonly author : string = 'Reda Baroudi , Smaaaak' ; 

   constructor(private readonly metaTagService : Meta , private readonly title : Title ) {}

   initDefaultMetaInformation(){

    this.title.setTitle(this.appTitle) ;

    this.metaTagService.addTags([
        { name : 'rebots' , content : 'index, follow' } ,
        { name : 'author' , content : this.author } ,
        { name : 'description' , content : this.description }
    ])

   }

   updateMetaInformationForPage(keywords : string , description : string , title : string ){

    this.title.setTitle(title) ;
    this.metaTagService.updateTag({name : 'keywords' , content : keywords })
    this.metaTagService.updateTag({ name : 'description' , content : description }) 

   }


}