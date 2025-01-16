import { Component } from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {Router, RouterLink} from '@angular/router';
import { FormsModule } from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  styleUrl: './login.component.css',
  imports: [FormsModule, HttpClientModule, MatInputModule, MatButtonModule, MatCardModule, RouterLink, NgIf]
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    const credentials = { username: this.username, password: this.password };
    this.http.post('http://localhost:8080/api/auth/login', credentials)
      .subscribe((response: any) => {
        console.log('Login successful', response);
        if (response.token) {
          localStorage.setItem('jwtToken', response.token);
          this.successMessage = 'Login successful! Redirecting to chat...';
          this.errorMessage = '';
          this.router.navigate(['/chat']);
        } else {
          this.errorMessage = 'Token not found in response';
          this.successMessage = '';
        }
      }, error => {
        this.errorMessage = 'Login failed. Please try again.';
        this.successMessage = '';
        console.error('Login failed', error);
      });
  }
}
