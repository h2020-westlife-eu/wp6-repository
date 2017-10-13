export class App {
  configureRouter(config, router) {
    config.title = 'Aurelia Router Demo';
    config.map([
      {route: ['', 'home'], name: 'home', moduleId: 'home/home', nav: true, title: 'Home'},
      {route: 'login', name: 'login', moduleId: 'login/login', nav: true, title: 'Login'},
      {route: 'visitingscientist', name: 'visitingscientist', moduleId: 'visitingscientist/visitingscientist', nav: true, title: 'Visiting Scientist'},
      {route: 'newproposal', name: 'fnewproposal', moduleId: 'visitingscientist/newproposal', nav: false, title: 'New Proposal'},
      {route: 'instructstaff', name: 'instructstaff', moduleId: 'instructstaff/instructstaff', nav: true, title: 'Instruct Staff'},
      {route: 'facilityadmin', name: 'facilityadmin', moduleId: 'facilityadmin/facilityadmin', nav: true, title: 'Facility Admin'}

    ]);
    this.router = router;
  }
}
