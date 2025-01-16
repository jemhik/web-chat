import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import { ChatService } from '../../service/chat/chat-service.service';
import { ChatMessage } from '../../model/chat-message';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule, MatCardTitle } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, HttpClientModule, NgForOf, MatFormFieldModule, MatCardModule, MatCardTitle, DatePipe, MatButtonModule, MatInputModule, NgIf]
})
export class ChatComponent implements OnInit, OnDestroy {
  messages: ChatMessage[] = [];
  messageForm: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient, private chatService: ChatService) {
    this.messageForm = this.fb.group({
      message: ['', [Validators.required, Validators.maxLength(200)]]
    });
  }

  ngOnInit() {
    const token = localStorage.getItem('jwtToken');

    if (token) {
      this.chatService.getChatHistory().subscribe((data) => {
        this.messages = data;
      });
      this.chatService.getMessages().subscribe((message: ChatMessage) => {
        console.log('New message received: ', message);
        this.messages.push(message);
      });
    }
  }

  ngOnDestroy() {
    this.chatService.disconnect();
  }

  sendMessage() {
    if (this.messageForm.valid) {
      const chatMessageDto = {
        senderId: '1',
        senderName: this.chatService.getUserNameFromJwt(),
        content: this.messageForm.value.message,
        timestamp: new Date()
      };

      this.chatService.sendMessage(chatMessageDto);
      this.messageForm.reset();
    }
  }
}
