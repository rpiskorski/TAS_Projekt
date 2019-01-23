import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  message: string;
  status: number;

  add(message: string,status: number) {
    this.message = message;
    this.status = status;
  }

  clear() {
    this.message = '';
    this.status = 0;
  }

}
