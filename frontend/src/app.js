export class App {

  configureRouter(config, router) {
    config.title = 'West-Life Repository Router';
    config.map([
      {route: ['', 'home'], name: 'home', moduleId: 'home/home', nav: true, title: 'Home'},
      {route: 'login', name: 'login', moduleId: 'login/login', nav: true, title: 'Login'},
      {route: 'visitingscientist', name: 'visitingscientist', moduleId: 'visitingscientist/visitingscientist', nav: true, title: 'Visiting Scientist'},
      {route: 'repositorystaff', name: 'repositorystaff', moduleId: 'repositorystaff/repositorystaff', nav: true, title: 'Support Staff'},
    ]);
    this.router = router;
  }

  login() {
    window.location="login.html?next"+window.location.pathname;
  }

}
