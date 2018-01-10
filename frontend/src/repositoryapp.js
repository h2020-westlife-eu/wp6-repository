export class Repositoryapp {

  configureRouter(config, router) {
    config.title = 'West-Life Repository Router';

    config.map([
      {route: ['', 'dashboard'], name: 'dashboard', moduleId: 'scientist/dashboard', nav: true, title: 'Dataset Dashboard'},
      {route: 'dashboard/project/:projectid/', name: 'projectdetail', moduleId: 'scientist/dashboard'},
      {route: 'repositorytovf', name: 'repositorytovf', moduleId: 'scientist/repositorytovf', nav: true, title: 'Upload to Virtual Folder'},
    ]);
    this.router = router;
  }

  login() {
    //window.location="login.html?next"+window.location.pathname;
  }

  activate(params, routeConfig){
    console.log("repository/app.activate()")
    console.log(params);
  }
}
