export class Repositoryapp {

  configureRouter(config, router) {
    config.title = 'West-Life Repository Router';

    config.map([
      {route: ['', 'dashboard'], name: 'dashboard', moduleId: 'scientist/dashboard', nav: true, title: 'Visitor Dashboard'},
      {route: 'dashboard/project/:projectid/', name: 'projectdetail', moduleId: 'scientist/dashboarddetail'},
      {route: 'dashboard/dataset/:datasetid/', name: 'datasetdetail', moduleId: 'scientist/dashboarddetail'},
      {route: 'createdataset', name: 'createdataset', moduleId: 'scientist/createdataset', nav: true, title: 'Create Empty Dataset'},
      {route: 'repositorytovf', name: 'repositorytovf', moduleId: 'scientist/repositorytovf', nav: true, title: 'Upload to Virtual Folder'},
    ]);
    this.router = router;
  }

  login() {
    //window.location="login.html?next"+window.location.pathname;
  }

}
