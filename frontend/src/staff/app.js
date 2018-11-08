import {PLATFORM} from 'aurelia-pal';

export class App {

  configureRouter(config, router) {
    config.title = 'West-Life Repository Router';
    config.map([
      {route: ['', 'dashboard'], name: 'dashboard', moduleId: PLATFORM.moduleName('staff/dashboard'), nav: true, title: 'Staff Dashboard'},
      {route: 'upselectuser', name: 'upselectuser', moduleId: PLATFORM.moduleName('staff/upselectuser'), nav: true, title: 'Upload - Select Visitor User'},
      {route: 'upselectdataset', name: 'upselectdataset', moduleId: PLATFORM.moduleName('staff/upselectdataset'), nav: true, title: 'Upload - Select/create target dataset'},
      {route: 'upselectdata', name: 'upselectdata', moduleId: PLATFORM.moduleName('staff/upselectdata'), nav: true, title: 'Upload - Select data'},
      {route: 'upconfirm', name: 'upconfirm', moduleId: PLATFORM.moduleName('staff/upconfirm'), nav: true, title: 'Upload - Confirmation'}
    ]);
    this.router = router;

  }

}
