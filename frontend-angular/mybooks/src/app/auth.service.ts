import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  isAuthenticated() {
      return this.getName();
  }
  
  name!: string;

  constructor(private http: HttpClient) { }

  getCustomer(username: string): Observable<String> {
    return this.http.get<String>(`http://localhost:8080/users/customer/${username}`);
  }

  getExpert(username: string): Observable<String> {
    return this.http.get<String>(`http://localhost:8080/users/expert/${username}`);
  }

  setName(name: string) {
    this.name = name;
    localStorage.setItem('name', name);
  }

  getName(): string {
    if (!this.name) {
      this.name = localStorage.getItem('name') as string;
    }
    return this.name;
  }

  createCustomer(name : string) : Observable<any>{
    return this.http.post<any>(`http://localhost:8080/users/customer`,name);
  }

  createExpert(name : string): Observable<any>{
    return this.http.post<any>(`http://localhost:8080/users/expert`,name);
  }


}
