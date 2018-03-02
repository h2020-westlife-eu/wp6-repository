export class Staffapp {

  configureRouter(config, router) {
    config.title = 'West-Life Repository Router';
    config.map([
      {route: ['', 'dashboard'], name: 'dashboard', moduleId: 'staff/dashboard', nav: true, title: 'Staff Dashboard'},
      {route: 'upselectuser', name: 'upselectuser', moduleId: 'staff/upselectuser', nav: true, title: 'Upload - Select Visitor User'},
      {route: 'upselectdataset', name: 'upselectdataset', moduleId: 'staff/upselectdataset', nav: true, title: 'Upload - Select/create target dataset'},
      {route: 'upselectdata', name: 'upselectdata', moduleId: 'staff/upselectdata', nav: true, title: 'Upload - Select data'},
      {route: 'upconfirm', name: 'upconfirm', moduleId: 'staff/upconfirm', nav: true, title: 'Upload - Confirmation'}
    ]);
    this.router = router;

  }

}
