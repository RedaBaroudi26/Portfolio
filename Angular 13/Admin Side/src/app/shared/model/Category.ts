export interface ICategory {

    idCategory : number ; 
    categoryName : string ;

}

export interface ICategoryPages {

    content : ICategory [] ;
    number : number ;
    totalElements : number ;
    size : number ;
    totalPages : number ;

}