export class App {

  configureRouter(config, router) {
    config.title = 'West-Life Repository Router';
    config.map([
      {route: ['', 'home'], name: 'home', moduleId: 'staff/repositorystaff', nav: true, title: 'Dataset Dashboard'}
    ]);
    this.router = router;

  }

  bind() {
    if (/(^|;)\s*JSESSIONID=/.test(document.cookie)) {
      //alert("Hello again!");
      console.log("User probably logged in")
    } else {
      window.location="/login.html?next"+window.location.pathname;
    }
  }

}
