Promise.config({
  warnings: {
    wForgottenReturn: false
  }
});

export function configure(aurelia) {
  aurelia.use
    .standardConfiguration()
    .feature(PLATFORM.moduleName('resources/index'));
//  aurelia.start().then(() => aurelia.setRoot(PLATFORM.moduleName('repositoryapp')));

  //  .feature('resources');
  aurelia.start().then(() => aurelia.setRoot(PLATFORM.moduleName('staffapp')));
}
