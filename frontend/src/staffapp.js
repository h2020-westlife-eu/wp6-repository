export class Staffapp {

  configureRouter(config, router) {
    config.title = 'West-Life Repository Router';
    config.map([
      {route: ['', 'home'], name: 'home', moduleId: 'staff/repositorystaff', nav: true, title: 'Dataset Dashboard'}
    ]);
    this.router = router;

  }

}
