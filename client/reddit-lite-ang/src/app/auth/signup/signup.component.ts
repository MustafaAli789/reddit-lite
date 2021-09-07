import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../shared/auth.service';
import { SignUpRequestPayload } from './signup-request.payload';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signupForm: FormGroup = new FormGroup({
    username: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required)
  });
  signUpRequestPayload: SignUpRequestPayload = {
    username: '',
    email: '',
    password: ''
  }

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) { }

  ngOnInit(): void {
  }

  signup() {
    this.signUpRequestPayload.email = this.signupForm?.get('email')?.value
    this.signUpRequestPayload.username = this.signupForm?.get('username')?.value
    this.signUpRequestPayload.password = this.signupForm?.get('password')?.value
    this.authService.signup(this.signUpRequestPayload)
      .subscribe(
        () => {
        this.router.navigate(['/login'], {
          queryParams: { registered: 'true' }
        })
      }, () => {
        this.toastr.error('Registration Failed! Please try again')
      })
  }

}
