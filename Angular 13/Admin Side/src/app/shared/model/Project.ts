import { IImage } from "./Image";

export interface IProject {

    idProject : number ;
    projectName : string ;
    description : string ;
    content : string ;
    rating : number ;
    clientName : string ;
    completed : boolean ;
    webSiteUrl : string ;
    completedDate : string ;
    images : IImage [] ;

}

export interface IProjectPages {

    content : IProject [] ;
    number : number ;
    totalElements : number ;
    size : number ;
    totalPages : number ;

}