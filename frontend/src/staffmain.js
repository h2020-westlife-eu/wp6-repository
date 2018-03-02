Promise.config({
  warnings: {
    wForgottenReturn: false
  }
});

export function configure(aurelia) {
  aurelia.use
    .standardConfiguration()
    .feature('resources');
  aurelia.start().then(() => aurelia.setRoot('staffapp'));
}
