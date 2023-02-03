import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ServiceComponent } from './service/service.component';
import { AboutComponent } from './about/about.component';
import { PortfolioComponent } from './portfolio/portfolio.component';
import { PortfolioDetailsComponent } from './portfolio-details/portfolio-details.component';
import { BlogDetailsComponent } from './blog/blog-details/blog-details.component';
import { ContactComponent } from './contact/contact.component';
import { SkillsComponent } from './skills/skills.component';
import { AllBlogsComponent } from './blog/all-blogs/all-blogs.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';
import { VisitorComponent } from './visitor/visitor.component';
import { Page404Component } from '../shared/page404/page404.component';
import { SideBlogComponent } from './blog/side-blog/side-blog.component';

const routes: Routes = [
  { path: '', component: Page404Component } ,
  {
    path: '',
    component: VisitorComponent,
    children: [
      { path: 'home', component: HomeComponent },
      { path: 'services', component: ServiceComponent },
      { path: 'about', component: AboutComponent },
      { path: 'portfolio', component: PortfolioComponent },
      { path: 'portfolioDetails/:projectName', component: PortfolioDetailsComponent },
      { path: 'blogDetails/:title', component: BlogDetailsComponent },
      { path: 'contact', component: ContactComponent },
      { path: 'skills', component: SkillsComponent } ,
      { path: 'blog', component: AllBlogsComponent } 
    ]
  }
];

@NgModule({
  declarations: [
    HomeComponent,
    ServiceComponent,
    AboutComponent,
    PortfolioComponent,
    PortfolioDetailsComponent,
    BlogDetailsComponent,
    ContactComponent,
    SkillsComponent,
    AllBlogsComponent,
    NavbarComponent,
    FooterComponent,
    VisitorComponent,
    SideBlogComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes)
  ]
})
export class VisitorModule { }
