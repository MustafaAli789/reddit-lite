import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { FormControl, FormGroup } from '@angular/forms';
import { AuthService } from '../shared/auth.service';
import { LoginRequestPayload } from './loginrequest.payload';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm?: FormGroup;
  loginReqPayload: LoginRequestPayload = {
    username: '',
    password: ''
  }

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    })
  }

  login() {
    this.loginReqPayload.password = this.loginForm?.get('password')?.value
    this.loginReqPayload.username = this.loginForm?.get('username')?.value
    this.authService.login(this.loginReqPayload).subscribe(_ => console.log('login successful'))
    
  }

}
