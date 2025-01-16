import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { ChatMessage } from '../../model/chat-message';
import SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private apiUrl = 'http://localhost:8080/api';
  private stompClient: Stomp.Client | null = null;
  private messageSubject = new Subject<ChatMessage>();

  constructor(private http: HttpClient) {
    this.initializeWebSocketConnection();
  }

  getChatHistory(): Observable<ChatMessage[]> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    return this.http.get<ChatMessage[]>(`${this.apiUrl}/history`, { headers });
  }

  initializeWebSocketConnection() {
    const token = localStorage.getItem('jwtToken');
    const socket = new SockJS(`http://localhost:8080/ws?token=${token}`);
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect(
      { 'Authorization': `Bearer ${token}` },
      (frame: any) => {
        console.log('Connected: ' + frame);
        if (this.stompClient) {
          this.addUser();
          this.stompClient.subscribe('/topic/public', (message) => {
            if (message.body) {
              const chatMessage = JSON.parse(message.body);
              console.log('Received message: ', chatMessage);
              this.messageSubject.next(chatMessage);
            }
          });
        }
      },
      (error: any) => {
        console.error('Connection error: ', error);
      }
    );
  }

  getMessages(): Observable<ChatMessage> {
    return this.messageSubject.asObservable();
  }

  addUser() {
    const chatMessageDto = {
      senderName: this.getUserNameFromJwt(),
      content: '',
      timestamp: new Date()
    };
    if (this.stompClient) {
      this.stompClient.send('/app/chat.addUser', {}, JSON.stringify(chatMessageDto));
    } else {
      console.error('stompClient is not initialized');
    }
  }

  sendMessage(chatMessageDto: any) {
    if (this.stompClient) {
      this.stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(chatMessageDto));
    } else {
      console.error('stompClient is not initialized');
    }
  }

  disconnect() {
    if (this.stompClient) {
      this.stompClient.disconnect(() => {
        console.log('Disconnected');
      });
    }
  }

  getUserNameFromJwt(): string | null {
    const token = localStorage.getItem('jwtToken');
    if (token) {
      const decoded: any = jwtDecode(token);
      return decoded?.username || null;
    } else {
      console.error('JWT token not found');
      return null;
    }
  }
}
