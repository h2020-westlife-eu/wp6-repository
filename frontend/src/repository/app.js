import {PLATFORM} from 'aurelia-pal';

export class App {

  configureRouter(config, router) {
    config.title = 'West-Life Repository Router';

    config.map([
      {route: ['', 'dashboard'], name: 'dashboard', moduleId: PLATFORM.moduleName('scientist/dashboard'), nav: true, title: 'Visitor Dashboard'},
      {route: 'dashboard/project/:projectid/', name: 'projectdetail', moduleId: PLATFORM.moduleName('scientist/dashboarddetail')},
      {route: 'dashboard/dataset/:datasetid/', name: 'datasetdetail', moduleId: PLATFORM.moduleName('scientist/dashboarddetail')},
      {route: 'createdataset', name: 'createdataset', moduleId: PLATFORM.moduleName('scientist/createdataset'), nav: true, title: 'Create Empty Dataset'},
      {route: 'repositorytovf', name: 'repositorytovf', moduleId: PLATFORM.moduleName('scientist/repositorytovf'), nav: true, title: 'Upload to Virtual Folder'}
    ]);
    this.router = router;
  }

}
