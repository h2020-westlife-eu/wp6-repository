export class App {
  constructor() {
    this.message = 'Hello World!';
  }

  login() {
    window.location="login.html?next"+window.location.pathname;
  }
}
