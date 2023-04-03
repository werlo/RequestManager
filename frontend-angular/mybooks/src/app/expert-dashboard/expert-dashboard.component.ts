import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthenticationService } from '../auth.service';
import { Task } from '../task';

@Component({
  selector: 'app-expert-dashboard',
  templateUrl: './expert-dashboard.component.html',
  styleUrls: ['./expert-dashboard.component.css']
})
export class ExpertDashboardComponent implements OnInit{

  constructor(private http : HttpClient,private authService : AuthenticationService){
  }
  ngOnInit(): void {
    this.showAllTask();
  }

  showAllTasks: boolean = true;
  tasksArray : any;
  myTasksArray : any;

  showAllTask() {
    this.showAllTasks =true;
    this.http.get(`http://localhost:8080/tasks`).subscribe(res => {
      console.log(res);
      this.tasksArray = res; // store tasks in a local variable
    });

  }

  showMyTask() {
    this.showAllTasks =false;
    this.http.get(`http://localhost:8080/tasks/experts/${this.authService.getName()}`).subscribe(res => {
      console.log(res);
      this.myTasksArray = res; // store tasks in a local variable
    });
  }

  resolve(task : Task){
    this.http.get(`http://localhost:8080/tasks/resolve/${this.authService.getName()}/${task.id}`).subscribe(res => {
      alert("Successfully resolved");
      this.showMyTask();
    });
  }

  pick(task : Task){
    this.http.get(`http://localhost:8080/tasks/pick/${this.authService.getName()}/${task.id}`).subscribe(res => {
      alert("Successfully picked task : " + task.name);
      this.showMyTask();
    });
  }

}
