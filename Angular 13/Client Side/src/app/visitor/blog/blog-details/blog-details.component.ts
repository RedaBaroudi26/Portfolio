import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import * as Aos from 'aos';
import { Observable } from 'rxjs';
import { IArticle, IArticlePages } from 'src/app/shared/model/Article';
import { IComment } from 'src/app/shared/model/Comment';
import { IReply } from 'src/app/shared/model/Reply';
import { ArticleService } from 'src/app/shared/services/article.service';
import { CommentService } from 'src/app/shared/services/comment.service';
import { ReplyService } from 'src/app/shared/services/reply.service';

@Component({
  selector: 'app-blog-details',
  templateUrl: './blog-details.component.html',
  styleUrls: ['./blog-details.component.scss']
})
export class BlogDetailsComponent implements OnInit {

  article : IArticle ;

  articlePage$ !: Observable<IArticlePages>;
  previousArticle !: IArticle ;
  nextArticle !: IArticle ;  

  commentForm !: FormGroup;
  name !: FormControl;
  email !: FormControl;
  message !: FormControl;

  hide: boolean = true;
  idComment: number;

  constructor(private title : Title ,private articleService: ArticleService, private route: ActivatedRoute, private commentService: CommentService, private replySercive: ReplyService ,private router : Router) { }

  ngOnInit(): void {

   this.getArticleByName() ;

    Aos.init();

    this.name = new FormControl('', Validators.required);
    this.email = new FormControl('', [Validators.required, Validators.email]);
    this.message = new FormControl('', Validators.required);
    this.commentForm = new FormGroup({
      name: this.name,
      email: this.email,
      message: this.message
    })

  }

  getArticleByName(){
  
    this.route.paramMap.subscribe((param) => {
      let name: string = param.get('title') ;
      this.articlePage$ = this.articleService.getArticlesByPages( 0 , 4 );
      this.articleService.getArticleByTitle(name).subscribe(
        (response : IArticle) => { this.article = response ;  this.title.setTitle(response.title) ; } ,
        (error : HttpErrorResponse) => { this.router.navigate(['/visitor/blog']) }
      )

    })
  
  }

  onSubmit(idArticle: number) {

    let pipe = new DatePipe('en-US');
    if (this.hide) {
      let comment: IComment = {
        name: this.commentForm.controls['name'].value,
        email: this.commentForm.controls['email'].value,
        message: this.commentForm.controls['message'].value,
        dateCreation: pipe.transform(Date.now(), 'yyyy-MM-dd HH:mm:ss') || '',
        replies: [],
        article: { idArticle : idArticle }
      };
      this.commentService.addComment(comment).subscribe(
        (response) => { this.commentForm.reset(); },
        (error) => { console.log(error) }
      )
    } else {
      let reply: IReply = {
        replyName: this.commentForm.controls['name'].value,
        email: this.commentForm.controls['email'].value,
        message: this.commentForm.controls['message'].value,
        dateCreation: pipe.transform(Date.now(), 'yyyy-MM-dd HH:mm:ss') || '',
        comment : { idComment : this.idComment }
      }
      this.replySercive.addReply(reply).subscribe(
        (response) => { this.commentForm.reset() },
        (error) => { console.log(error) }
      ) 
    }

  }

  reply(idComment: number, name: string) {

    this.commentForm.controls['message'].setValue( '@' + name + ' ');
    this.hide = false;
    this.idComment = idComment;

  }

  cancelReply(){
    this.commentForm.controls['message'].setValue("");
    this.hide = true;
    this.idComment = null;
  }

  getImage(idArticle: number, idImage: number) {
    return this.articleService.getArticleImage(idArticle, idImage);
  }

}
