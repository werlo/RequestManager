import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthenticationService } from '../auth.service';
import { Task, TaskStatus } from '../task';

@Component({
  selector: 'app-request-form',
  templateUrl: './request-form.component.html',
  styleUrls: ['./request-form.component.css']
})
export class RequestFormComponent implements OnInit {
  requestForm: FormGroup;
  showForm=true;
  tasksArray : any;

  constructor(private fb: FormBuilder, private http: HttpClient,private authService : AuthenticationService) {
    this.requestForm = this.fb.group({
      tasks: this.fb.array([])
    });
  }

  ngOnInit(): void {}

  get tasks() {
    return this.requestForm.get('tasks') as FormArray;
  }

  createTask(): FormGroup {
    return this.fb.group({
      taskName: ['', Validators.required],
      description: [''],
      deadline: ['', Validators.required],
      estimatedEffort: ['', Validators.required]
    });
  }

  addTask() {
    this.tasks.push(this.createTask());
  }

  deleteTask(index: number) {
    this.tasks.removeAt(index);
  }

  onSubmit() {
    const form = this.requestForm;
    this.http.post(`http://localhost:8080/tasks/customers/${this.authService.getName()}`,this.setData(form)).subscribe(
      (response) => {
        alert('Success! Request submitted.');
        console.log('Success!', response);
      },
      (error) => {
        alert('Error! Request could not be submitted.');
        console.error('Error!', error);
      }
    );
  }

  showStatus(){
    this.showForm = false;
    this.http.get(`http://localhost:8080/tasks/customers/${this.authService.getName()}`).subscribe(res => {
      console.log(res);
      this.tasksArray = res; // store tasks in a local variable
    });
  }
  setData(form: FormGroup): any {
  const tasks = form.value.tasks.map((taskData: any, index: number) => {
    const task = new Task();
    task.id = index + 1;
    task.name = taskData.taskName;
    task.description = taskData.description;
    task.deadline = new Date(taskData.deadline);
    task.estimatedEffort = taskData.estimatedEffort;
    task.orderNum = index + 1;
    task.status = TaskStatus.QUEUED;
    return task;
  });
  return tasks ;
}
}


