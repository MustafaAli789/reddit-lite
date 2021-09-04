import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SignUpRequestPayload } from '../signup/signup-request.payload';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  signup(signUpRequestPayload: SignUpRequestPayload):Observable<any> {
    return this.httpClient.post('http://localhost:8080/api/auth/signup', signUpRequestPayload, {responseType: 'text'})
  }
}
