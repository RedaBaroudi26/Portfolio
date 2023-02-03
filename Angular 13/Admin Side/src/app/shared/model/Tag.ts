export interface ITag {

    idTag : number ;
    tagName : string ;

}

export interface ITagPages {

    content : ITag [] ;
    number : number ;
    totalElements : number ;
    size : number ;
    totalPages : number ;

}