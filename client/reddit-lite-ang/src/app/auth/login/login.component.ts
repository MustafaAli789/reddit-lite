import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../shared/auth.service';
import { LoginRequestPayload } from './loginrequest.payload';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm?: FormGroup;
  registerSuccessMessage = ""
  isError: boolean = false
  loginReqPayload: LoginRequestPayload = {
    username: '',
    password: ''
  }

  constructor(private authService: AuthService, private router: Router, 
    private toastr: ToastrService, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    })
    this.activatedRoute.queryParams
      .subscribe(params => {
        if (params['registered'] !== undefined && params['registered'] === 'true') {
          this.toastr.success('Signup Successful');
          this.registerSuccessMessage = 'Please Check your inbox for activation link. You msut activate your account before login.'
        }
      })
  }

  login() {
    this.loginReqPayload.password = this.loginForm?.get('password')?.value
    this.loginReqPayload.username = this.loginForm?.get('username')?.value
    this.authService.login(this.loginReqPayload).subscribe(data => {
      if (data) {
        this.isError = false;
      } else {
        this.isError = true;
      }
    })
    
  }

}
