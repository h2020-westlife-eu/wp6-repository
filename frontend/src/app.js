export class App {

  configureRouter(config, router) {
    config.title = 'West-Life Repository Router';
    config.map([
      {route: ['', 'home'], name: 'home', moduleId: 'visitingscientist/visitingscientist', nav: true, title: 'Dataset Dashboard'},
      {route: 'repositorystaff', name: 'repositorystaff', moduleId: 'repositorystaff/repositorystaff', nav: true, title: 'File Upload Tool'},
    ]);
    this.router = router;
  }

  login() {
    window.location="login.html?next"+window.location.pathname;
  }

}
