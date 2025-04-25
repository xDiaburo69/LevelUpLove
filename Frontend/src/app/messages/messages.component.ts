// messages.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-messages',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent {
  // Liste der Chats
  chats = ['Chat mit Batman', 'Chat mit Wonder Woman', 'Chat mit Superman'];
  selectedChat = this.chats[0];

  // Nachrichten pro Chat
  messagesMap: Record<string, string[]> = {
    'Chat mit Batman': [
      'Batman: Ich bin bereit für die Mission.',
      'Du: Perfekt, ich auch. Lass uns loslegen!'
    ],
    'Chat mit Wonder Woman': [
      'Wonder Woman: Seid ihr bereit?',
      'Du: Absolut!'
    ],
    'Chat mit Superman': [
      'Superman: Schnell wie der Blitz!',
      'Du: Auf geht’s!'
    ]
  };

  // Aktuelle Nachrichten
  messages = [...this.messagesMap[this.selectedChat]];

  // Eingabetext
  messageInput = '';

  // Sidebar geöffnet?
  sidebarOpen = true;

  // Notification-Text
  notification: string | null = null;

  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
  }

  selectChat(chat: string) {
    this.selectedChat = chat;
    this.messages = [...this.messagesMap[chat]];
    this.notification = null;
  }

  sendMessage() {
    const text = this.messageInput.trim();
    if (!text) return;
    this.messages.push(`Du: ${text}`);
    this.messageInput = '';

    setTimeout(() => {
      const name = this.selectedChat.split(' ')[2];
      const reply = `${name}: Danke für deine Nachricht!`;
      this.messages.push(reply);
      this.notification = `Neue Nachricht von ${name}!`;
      setTimeout(() => this.notification = null, 3000);
    }, 1000);
  }
}
