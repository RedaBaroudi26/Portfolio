import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { ArticleComponent } from './article/allArticles/article.component';
import { CategoryComponent } from './category/category.component';
import { TagComponent } from './tag/tag.component';
import { Page404Component } from '../shared/page404/page404.component';
import { ProjectsComponent } from './project/allProjects/project.component';
import { UpdateArticleComponent } from './article/update-article/update-article.component';
import { UpdateProjectComponent } from './project/update-project/update-project.component';

import { FullComponent } from './full/full.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { SidebarComponent } from './layouts/sidebar/sidebar.component';
import { HeaderComponent } from './layouts/header/header.component';
import { HttpClientModule } from '@angular/common/http';
import { AddArticleComponent } from './article/add-article/add-article.component';
import { AddProjectComponent } from './project/add-project/add-project.component';
import { LoginGuard } from '../shared/guards/login.guard';
import { ProfileComponent } from './profile/profile.component';
import { SkillsComponent } from './skill/skills/skills.component';
import { AddSkillComponent } from './skill/add-skill/add-skill.component';
import { UpdateSkillComponent } from './skill/update-skill/update-skill.component';


const routes: Routes = [
  { 
    path: '' , component: AdminComponent ,
    children : [
      { path: '' , component : Page404Component   } ,
      { path: 'login', component : LoginComponent   } ,
      { path: '', component : FullComponent , canActivate : [LoginGuard] ,
        children : [ 
          { path : 'dashboard' , component : DashboardComponent } ,
          { path : 'project' , component : ProjectsComponent } ,
          { path : 'addProject' , component : AddProjectComponent } ,
          { path : 'updateProject/:id' , component : UpdateProjectComponent } ,
          { path : 'article' , component : ArticleComponent } ,
          { path : 'addArticle' , component : AddArticleComponent } ,
          { path : 'updateArticle/:id' , component : UpdateArticleComponent } ,
          { path : 'category' , component : CategoryComponent } , 
          { path : 'tag' , component : TagComponent } ,
          { path : 'profile' , component : ProfileComponent } ,
          { path : 'skill' , component : SkillsComponent } ,
          { path : 'addSkill' , component : AddSkillComponent } ,
          { path : 'updateSkill/:id' , component : UpdateSkillComponent }
         ]  }
    ] 
  } ,
  
];


@NgModule({
  declarations: [
    FullComponent ,
    UpdateProjectComponent ,
    CategoryComponent ,
    TagComponent ,
    DashboardComponent,
    LoginComponent,
    FooterComponent ,
    SidebarComponent ,
    HeaderComponent ,
    AdminComponent , 
    ProjectsComponent ,
    UpdateArticleComponent ,
    AddArticleComponent,
    AddProjectComponent ,
    ArticleComponent,
    ProfileComponent,
    SkillsComponent,
    AddSkillComponent,
    UpdateSkillComponent 
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule ,
    RouterModule.forChild(routes)
  ]
})
export class AdminModule { }
