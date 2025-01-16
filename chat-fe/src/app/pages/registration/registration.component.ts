import { Component } from '@angular/core';
import {HttpClient, HttpClientModule, HttpResponse} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {MatCard, MatCardContent, MatCardTitle} from '@angular/material/card';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatButton} from '@angular/material/button';
import {MatInput} from '@angular/material/input';
import {Router, RouterLink} from '@angular/router';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-registration',
  standalone: true,
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css',
  imports: [FormsModule, HttpClientModule, MatCard, MatCardTitle, MatCardContent, MatFormField, MatButton, MatInput, RouterLink, MatLabel, NgIf]
})
export class RegistrationComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private http: HttpClient, private router: Router) {}


  onSubmit() {
    const user = { username: this.username, password: this.password };
    this.http.post<HttpResponse<any>>('http://localhost:8080/api/auth/register', user, { observe: 'response' })
      .subscribe(response => {
        if (response.status === 200) {
          console.log('Registration successful', response);
          this.errorMessage = ''; // Clear error message on success
          this.router.navigate(['/login']);
        } else {
          this.errorMessage = 'Registration failed. Please try again.';
          console.error('Registration failed', response);
        }
      }, error => {
        this.errorMessage = 'Registration failed. Please try again.';
        console.error('Registration failed', error);
      });
  }
}
