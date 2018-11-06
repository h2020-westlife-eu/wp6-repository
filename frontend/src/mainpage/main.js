//Configure Bluebird Promises not to throw warnings about aurelia empty promises
Promise.config({
  warnings: {
    wForgottenReturn: false
  }
});

import {PLATFORM} from 'aurelia-pal';

export function configure(aurelia) {
  aurelia.use
    .standardConfiguration()
    .feature(PLATFORM.moduleName('resources/index'));
  aurelia.start().then(() => aurelia.setRoot(PLATFORM.moduleName('mainpage/app')));
  PLATFORM.moduleName('repository/main');
  PLATFORM.moduleName('staffmain');
  }
