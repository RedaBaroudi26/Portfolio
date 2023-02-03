import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


const routes: Routes = [
  { path : 'visitor' , loadChildren : () => import("./visitor/visitor.module").then(m => m.VisitorModule) } ,
  { path : '**' , redirectTo : 'visitor/home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
