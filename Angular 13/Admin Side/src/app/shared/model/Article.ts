import { ICategory } from "./Category";
import { IComment } from "./Comment";
import { IImage } from "./Image";

export interface IArticle {

    idArticle : number ;
    title : string ;
    description : string ;
    content : string ;
    dateCreation : string ;
    writerName : string ;
    category : ICategory ; 
    comments : IComment [] ;
    images : IImage [] ;

}

export interface IArticlePages {

    content : IArticle [] ;
    number : number ;
    totalElements : number ;
    size : number ;
    totalPages : number ;

}