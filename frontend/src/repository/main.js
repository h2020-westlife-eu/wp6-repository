//Configure Bluebird Promises not to throw warnings about aurelia empty promises

import {PLATFORM} from 'aurelia-pal';
import 'babel-polyfill';
import * as Bluebird from 'bluebird';

Bluebird.config({warnings: {wForgottenReturn:true}, longStackTraces: true});


export function configure(aurelia) {
  aurelia.use
    .standardConfiguration()
    .feature(PLATFORM.moduleName('resources/index'));
  aurelia.start().then(() => aurelia.setRoot(PLATFORM.moduleName('repository/app')));
}
