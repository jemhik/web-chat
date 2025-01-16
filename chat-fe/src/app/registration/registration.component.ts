import { Component } from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {MatCard, MatCardContent, MatCardTitle} from '@angular/material/card';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatButton} from '@angular/material/button';
import {MatInput} from '@angular/material/input';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-registration',
  standalone: true,
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css',
  imports: [FormsModule, HttpClientModule, MatCard, MatCardTitle, MatCardContent, MatFormField, MatButton, MatInput, RouterLink, MatLabel]
})
export class RegistrationComponent {
  username: string = '';
  password: string = '';

  constructor(private http: HttpClient) {}

  onSubmit() {
    const user = { username: this.username, password: this.password };
    this.http.post('http://localhost:8080/api/auth/register', user)
      .subscribe(response => {
        console.log('Registration successful', response);
      }, error => {
        console.error('Registration failed', error);
      });
  }
}
