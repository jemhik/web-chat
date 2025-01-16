import { Injectable } from '@angular/core';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) { }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('jwtToken');
  }

  logout() {
    localStorage.removeItem('jwtToken');
    this.router.navigate(['/login']).then(r => console.log('Navigated to chat'));
  }
}
