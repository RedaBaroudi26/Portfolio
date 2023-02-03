import { IComment } from "./Comment";

export interface IReply {
 
    idReply : number ;
    replyName : string ;
    email : string ;
    message : string ;
    dateCreation : Date ;
    comment : IComment ; 
    
}