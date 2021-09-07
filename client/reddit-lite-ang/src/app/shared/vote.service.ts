import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { VotePayload } from './vote-button/vote-payload';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VoteService {

  constructor(private http: HttpClient) { }

  baseUrl = environment.baseUrl

  vote(votePayload: VotePayload): Observable<any> {
    return this.http.post(this.baseUrl+'/api/votes/', votePayload);
  }
}