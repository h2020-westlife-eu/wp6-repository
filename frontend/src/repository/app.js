export class App {

  configureRouter(config, router) {
    config.title = 'West-Life Repository Router';
    config.map([
      {route: ['', 'home'], name: 'home', moduleId: 'scientist/visitingscientist', nav: true, title: 'Dataset Dashboard'},
      {route: 'repositorytovf', name: 'repositorytovf', moduleId: 'scientist/repositorytovf', nav: true, title: 'Upload to Virtual Folder'},
    ]);
    this.router = router;
  }

  login() {
    //window.location="login.html?next"+window.location.pathname;
  }

}
