import { IAccount } from "./Account";
import { ISocialMedia } from "./SocialMedia";

export interface IUser{

    idUser ?: number ;
    fullName ?: string ;
    dateCreation ?: string ;
    about ?: string ;
    phone ?: string ;
    address ?: string ;
    job ?: string ;
    country ?: string ;
    company ?: string ;
    socialMedia ?: ISocialMedia ;
    account ?: IAccount ; 

}