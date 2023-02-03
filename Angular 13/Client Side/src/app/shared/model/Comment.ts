import { IArticle } from "./Article";
import { IReply } from "./Reply";

export interface IComment {

    idComment ?: number ; 
    name ?: string ;
    email ?: string ;
    message ?: string ;
    dateCreation ?: string ;
    replies ?: IReply [] ;
    article ?: IArticle ;

}