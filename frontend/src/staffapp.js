export class Staffapp {

  configureRouter(config, router) {
    config.title = 'West-Life Repository Router';
    config.map([
      {route: ['', 'dashboard'], name: 'dashboard', moduleId: 'staff/repositorystaff', nav: true, title: 'Visitor Experiment Dashboard'},
      {route: 'dataupload', name: 'dataupload', moduleId: 'staff/dataupload', nav: true, title: 'Visitor Dataset Upload'}
    ]);
    this.router = router;

  }

}
