export interface ICategory {

    categoryName : string ;
    numberOfArticles : number

}

export interface ICategoryPages {

    content : ICategory [] ;
    number : number ;
    totalElements : number ;
    size : number ;
    totalPages : number ;

}