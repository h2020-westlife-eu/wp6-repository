//Configure Bluebird Promises not to throw warnings about aurelia empty promises
Promise.config({
  warnings: {
    wForgottenReturn: false
  }
});

export function configure(aurelia) {
  aurelia.use.basicConfiguration();
  aurelia.start().then(() => aurelia.setRoot());

  }
