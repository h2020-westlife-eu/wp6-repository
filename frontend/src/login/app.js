import {HttpClient} from 'aurelia-http-client';

export class App {
  static inject = [HttpClient];

constructor(httpclient){
    this.location = window.location.protocol;
    this.islocalhost= this.location.startsWith('http:');
    this.client=httpclient;

  }


}
