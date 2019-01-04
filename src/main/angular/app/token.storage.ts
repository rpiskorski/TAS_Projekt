import { Injectable } from '@angular/core';

const TOKEN_KEY = 'TOKEN_KEY';
const USERNAME = 'USERNAME';
const MESSAGE = 'MESSAGE';
const STATUS = 'STATUS';

@Injectable({
  providedIn: 'root'
})
export class TokenStorage{

  constructor(){}

  public setToken(token: string){
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY,token);
  }

  public setUsername(name: string){
    window.sessionStorage.setItem(USERNAME,name);
  }
  public setMessage(message: string){
    window.sessionStorage.removeItem(MESSAGE);
    window.sessionStorage.setItem(MESSAGE,message);
  }

  public setStatus(status: number){
    window.sessionStorage.removeItem(STATUS);
    window.sessionStorage.setItem(STATUS,status.toString());
  }


  logout(){
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.removeItem(USERNAME);
    window.sessionStorage.removeItem(MESSAGE);
    window.sessionStorage.removeItem(STATUS);
    window.sessionStorage.clear();

  }
  public getToken(): string{
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public getUsername(): string{
    return window.sessionStorage.getItem(USERNAME);
  }

  public getMessage(): string{
    let message = window.sessionStorage.getItem(MESSAGE);
    window.sessionStorage.removeItem(MESSAGE);
    return message;
  }



  public getStatus(): number{
    let status = parseInt(window.sessionStorage.getItem(STATUS),10);
    window.sessionStorage.removeItem(STATUS);
    return status;
  }
}
