import { IImage } from "./Image";

export interface ISkill{

    idSkill ?: number ;
    skillName ?: string ;
    percentage ?: number ; 
    image ?: IImage ;

}

export interface ISkillPage {

    content : ISkill [] ;
    number : number ;
    totalElements : number ;
    size : number ;
    totalPages : number ;

}