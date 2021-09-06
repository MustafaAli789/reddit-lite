import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/shared/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PostModel } from 'src/app/shared/post-model';
import { throwError } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CommentPayload } from 'src/app/comment/comment.payload';
import { CommentService } from 'src/app/comment/comment.service';
import { AuthService } from 'src/app/auth/shared/auth.service';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {

  postId: number;
  post: PostModel = {id: 0};
  commentForm: FormGroup = new FormGroup({
    text: new FormControl('', Validators.required)
  });
  commentPayload: CommentPayload;
  comments: CommentPayload[] = [];
  loggedIn: boolean = false;

  constructor(private postService: PostService, private activateRoute: ActivatedRoute,
    private commentService: CommentService, private router: Router, private authService: AuthService) {
    this.postId = this.activateRoute.snapshot.params.id;
    this.commentPayload = {
      text: '',
      postId: this.postId,
      username: authService.getUserName()
    };
  }

  ngOnInit(): void {
    this.getPostById();
    this.getCommentsForPost();
    this.loggedIn = this.authService.isLoggedIn()
    this.authService.loggedIn.subscribe(data => this.loggedIn = data)
  }

  postComment() {
    this.commentPayload.text = this.commentForm.get('text')?.value;
    this.commentService.postComment(this.commentPayload).subscribe(() => {
      this.commentForm.get('text')?.setValue('');
      this.getCommentsForPost();
    }, (error: any) => {
      throwError(error);
    })
  }

  private getPostById() {
    this.postService.getPost(this.postId).subscribe(data => {
      this.post = data;
    }, error => {
      throwError(error);
    });
  }

  private getCommentsForPost() {
    this.commentService.getAllCommentsForPost(this.postId).subscribe((data: any[]) => {
      this.comments = data;
    }, (error: any) => {
      throwError(error);
    });
  }

}