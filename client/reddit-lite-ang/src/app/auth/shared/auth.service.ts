import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators'
import { LoginResponse } from '../login/login-response.payload';
import { LoginRequestPayload } from '../login/loginrequest.payload';
import { SignUpRequestPayload } from '../signup/signup-request.payload';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient, private localStorage: LocalStorageService) { }

  signup(signUpRequestPayload: SignUpRequestPayload):Observable<any> {
    return this.httpClient.post('http://localhost:8080/api/auth/signup', signUpRequestPayload, {responseType: 'text'})
  }
  login(loginReq: LoginRequestPayload): Observable<boolean> {
    return this.httpClient.post<LoginResponse>('http://localhost:8080/api/auth/login', loginReq)
      .pipe(
        map(data => {
          this.localStorage.store("authenticationToken", data.authenticationToken)
          this.localStorage.store("username", data.username)
          this.localStorage.store("refreshToken", data.refreshToken)
          this.localStorage.store("expiresAt", data.expiresAt)

          return true
        })
      )
  }
}
