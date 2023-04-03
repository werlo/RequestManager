import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../auth.service';

@Component({
  selector: 'app-login',
  template: `
    <div class="login-container">
      <h1>Welcome to MyBooks</h1>
      <ng-container *ngIf="!showCreateForm">
        <form>
          <label for="username">Username:</label>
          <input type="text" id="username" [(ngModel)]="username" name="username" required>
          <button type="submit" class="customer-button" (click)="loginAsCustomer()">Login as Customer</button>
          <button type="submit" class="expert-button" (click)="loginAsExpert()">Login as Expert</button>
        </form>
        <div class="signup-options">
          <p>Don't have an account yet?</p>
          <button class="customer-button" (click)="showCreateForm = true">Create Customer Account</button>
          <button class="expert-button" (click)="showCreateForm = true">Create Expert Account</button>
        </div>
      </ng-container>
      <ng-container *ngIf="showCreateForm">
        <form>
          <label for="username">Username:</label>
          <input type="text" id="username" [(ngModel)]="newUsername" name="username" required>
          <button type="submit" class="customer-button" (click)="createNewCustomer()">Create Customer Account</button>
          <button type="submit" class="expert-button" (click)="createNewExpert()">Create Expert Account</button>
        </form>
        <div class="signup-options">
          <p>Already have an account?</p>
          <button class="customer-button" (click)="showCreateForm = false">Login as Customer</button>
          <button class="expert-button" (click)="showCreateForm = false">Login as Expert</button>
        </div>
      </ng-container>
    </div>
  `,
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  newUsername: string = '';
  showCreateForm: boolean = false;

  constructor(private authService: AuthenticationService,private router: Router) { }

  loginAsCustomer() {
    this.authService.getCustomer(this.username).subscribe(
      (user : any) => {
        if (user) {
          this.authService.setName(user.name);
          this.router.navigate(['/request-form']);
        } else {
          alert('Invalid username or password!');
        }
      },
      (error : any) => {
        console.log(error);
        alert('Error occurred while logging in!');
      }
    );
  }

  loginAsExpert() {
    this.authService.getExpert(this.username).subscribe(
      (user : any) => {
        if (user) {
          this.authService.setName(user.name);
          this.router.navigate(["expert-dashboard"]);
        } else {
          alert('Invalid username or password!');
        }
      },
      (error : any) => {
        console.log(error);
        alert('Error occurred while logging in!');
      }
    );
  }

  createNewCustomer() {
    const newUser = this.newUsername;
    this.authService.createCustomer(newUser).subscribe(
      (response : any) => {
          this.authService.setName(this.newUsername);
          alert("Successfully created user : "+this.newUsername);
          this.router.navigate(['/request-form']);
          
        }
      ,
      (error : any) => {
        console.log(error);
        alert('Error occurred while creating account!');
      }
    );
  }

  createNewExpert(){
    const newUser = this.newUsername;
    this.authService.createExpert(newUser).subscribe(
      (response : any) => {
          this.authService.setName(this.newUsername);
          //this.router.navigate(['/dashboard']);
          alert("Successfully created expert : "+this.newUsername);
        
      },
      (error : any) => {
        console.log(error);
        alert('Error occurred while creating account!');
      }
    );
  }
}